package com.example.customerservice.demo.controller;

import com.example.customerservice.demo.model.CustomerRequest;
import com.example.customerservice.demo.dao.dto.Customer;
import com.example.customerservice.demo.mapper.CustomerMapper;
import com.example.customerservice.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Slice<Customer>> getAllCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @PageableDefault(size = 2) Pageable pageable) {

        Slice<Customer> customers = customerService.getCustomersByCriteria(name, city, state, pageable);
        return ResponseEntity.ok(customers);
    }


}
