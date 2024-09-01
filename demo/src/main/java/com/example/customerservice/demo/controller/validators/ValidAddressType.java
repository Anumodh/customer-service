package com.example.customerservice.demo.controller.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressTypeValidator.class)
public @interface ValidAddressType {
    String message() default "Invalid address type. Allowed values are: Home, Work, Other.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
