package com.example.customerservice.demo.service;

import com.example.customerservice.demo.dao.CustomerRepository;
import com.example.customerservice.demo.dao.dto.Customer;
import com.example.customerservice.demo.messaging.MessageProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  MessageProducer messageProducer;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Slice<Customer> getAllCustomers(Pageable pageable) {
      return customerRepository.findAll(pageable);
  }


    public Customer saveCustomer(Customer customer) {

      Customer savedCustomer = customerRepository.save(customer);
      messageProducer.sendMessage(customer);
      return savedCustomer;
      
    }

    public Customer getCustomerById(Long id) {
      return customerRepository.findById(id).orElse(null);
    }

    public Slice<Customer> getCustomersByState(String state) {
      return customerRepository.findByAddress_State(state);
  }

}
