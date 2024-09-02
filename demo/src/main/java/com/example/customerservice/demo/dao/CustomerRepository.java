package com.example.customerservice.demo.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.customerservice.demo.dao.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c JOIN c.address a WHERE "
            + "(:name IS NULL OR c.firstName = :name) AND "
            + "(:city IS NULL OR a.city = :city) AND "
            + "(:state IS NULL OR a.state = :state)")
    Slice<Customer> findCustomersByCriteria(
            @Param("name") String name,
            @Param("city") String city,
            @Param("state") String state,
            Pageable pageable);
}