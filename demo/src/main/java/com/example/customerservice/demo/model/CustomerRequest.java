package com.example.customerservice.demo.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String customerId;
    private Integer age;
    private BigDecimal spendingLimit;
    private String mobileNumber;
    private List<AddressRequest> address;
    
}

