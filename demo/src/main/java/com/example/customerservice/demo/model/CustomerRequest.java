package com.example.customerservice.demo.model;

import java.util.List;

import com.example.customerservice.demo.controller.validators.UniqueAddressTypes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Customer ID is required")
    private String customerId;

    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 120, message = "Age must be less than or equal to 120")
    private Integer age;

    @DecimalMin(value = "0.0", inclusive = false, message = "Spending limit must be greater than 0")
    private Double spendingLimit;

    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be a valid 10-digit number")
    private String mobileNumber;

    @Valid
    @NotEmpty(message = "At least one address is required")
    @UniqueAddressTypes(message = "Duplicate address types are not allowed. Only one address per type is allowed.")
    private List<AddressRequest> address;

}
