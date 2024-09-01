package com.example.customerservice.demo.controller; 

import com.example.customerservice.demo.model.CustomerRequest;
import com.example.customerservice.demo.dao.dto.Customer;
import com.example.customerservice.demo.mapper.CustomerMapper;
import com.example.customerservice.demo.service.CustomerService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.validation.BindingResult;




// import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {

        Customer customer = CustomerMapper.toEntity(customerRequest);
        Customer customerResp = customerService.saveCustomer(customer);
        return new ResponseEntity<>(customerResp, HttpStatus.CREATED);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
    Customer customer = customerService.getCustomerById(id);
    
    if (customer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.ok(customer);
}

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(required = false) String state) {
        List<Customer> customers;

        if (state != null && !state.isEmpty()) {
            customers = customerService.getCustomersByState(state);
        } else {
            customers = customerService.getAllCustomers();
        }
        return ResponseEntity.ok(customers);
    }
	
}
