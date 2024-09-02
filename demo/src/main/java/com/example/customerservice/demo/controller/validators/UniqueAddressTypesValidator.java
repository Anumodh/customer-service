package com.example.customerservice.demo.controller.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.customerservice.demo.model.AddressRequest;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class UniqueAddressTypesValidator implements ConstraintValidator<UniqueAddressTypes, List<AddressRequest>> {

    @Override
    public boolean isValid(List<AddressRequest> addressList, ConstraintValidatorContext context) {
        if (addressList == null || addressList.isEmpty()) {
            return true;
        }

        Set<String> types = new HashSet<>();
        for (AddressRequest address : addressList) {
            if (!types.add(address.getType().toLowerCase())) { 
                return false; // Duplicate type found
            }
        }
        return true;
    }
}

