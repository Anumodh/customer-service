package com.example.customerservice.demo.dao.dto;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String customerId;
    private Integer age;
    private BigDecimal spendingLimit;
    private String mobileNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Address> address;

    // Getters and Setters
}

