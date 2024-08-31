package com.example.customerservice.demo.dao;

import com.example.customerservice.demo.dao.dto.Customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByAddress_State(String state);

}

