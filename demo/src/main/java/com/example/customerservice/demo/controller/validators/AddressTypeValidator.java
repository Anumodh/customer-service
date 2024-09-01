package com.example.customerservice.demo.controller.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.example.customerservice.demo.model.AddressType;

public class AddressTypeValidator implements ConstraintValidator<ValidAddressType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // `@NotBlank` or `@NotNull` should handle null cases
        }

        try {
            AddressType.valueOf(value.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

