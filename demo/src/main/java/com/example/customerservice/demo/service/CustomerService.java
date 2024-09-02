package com.example.customerservice.demo.service;

import com.example.customerservice.demo.dao.CustomerRepository;
import com.example.customerservice.demo.dao.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
  private static final String TOPIC = "customers";

  private final CustomerRepository customerRepository;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Slice<Customer> getAllCustomers(Pageable pageable) {
    return customerRepository.findAll(pageable);
  }

  @Transactional
  public Customer saveCustomer(Customer customer) {

    Customer savedCustomer = customerRepository.save(customer);

    ObjectMapper objectMapper = new ObjectMapper();
    String customerJson;

    try {
      customerJson = objectMapper.writeValueAsString(customer);
      sendMessage(customerJson);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("failed to parse JSON");
    }

    if (savedCustomer.getFirstName().equals("test_error")) {
      throw new RuntimeException("transaction failed");
    }

    return savedCustomer;

  }

  @Transactional("kafkaTransactionManager")
  public void sendMessage(String customerJSON) {
    kafkaTemplate.send(TOPIC, customerJSON);
  }

  public Customer getCustomerById(Long id) {
    return customerRepository.findById(id).orElse(null);
  }

  public Slice<Customer> getCustomersByState(String state) {
    return customerRepository.findByAddress_State(state);
  }

}
