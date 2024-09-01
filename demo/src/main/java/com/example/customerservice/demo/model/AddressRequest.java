package com.example.customerservice.demo.model;

import com.example.customerservice.demo.controller.validators.ValidAddressType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AddressRequest {

    @NotBlank
    @ValidAddressType
    private String type;

    @NotBlank(message = "Address line 1 is required")
    private String address1;

    private String address2; // Optional, no validation

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @Pattern(regexp = "^\\d{5}$", message = "Zip code must be a 5-digit number")
    private String zipCode;


    // Getters and Setters
}
