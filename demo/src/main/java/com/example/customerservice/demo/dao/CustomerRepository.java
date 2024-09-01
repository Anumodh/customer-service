package com.example.customerservice.demo.dao;

import com.example.customerservice.demo.dao.dto.Customer;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Slice<Customer> findByAddress_State(String state);

}

