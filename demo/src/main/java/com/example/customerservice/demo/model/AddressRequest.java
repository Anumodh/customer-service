package com.example.customerservice.demo.model;

import lombok.Data;

@Data
public class AddressRequest {

    private String type;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;

    // Getters and Setters
}
