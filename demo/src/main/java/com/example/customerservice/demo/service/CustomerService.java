package com.example.customerservice.demo.service;

import com.example.customerservice.demo.dao.CustomerRepository;
import com.example.customerservice.demo.dao.dto.Customer;
// import com.example.customerservice.demo.mapper.CustomerMapper;
// import com.example.customerservice.demo.model.CustomerRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
      return customerRepository.findAll();
  }


    public Customer saveCustomer(Customer customer) {
      return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
      return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getCustomersByState(String state) {
      return customerRepository.findByAddress_State(state);
  }

}
