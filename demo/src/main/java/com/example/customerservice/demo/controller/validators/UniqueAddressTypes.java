package com.example.customerservice.demo.controller.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueAddressTypesValidator.class)
public @interface UniqueAddressTypes {
    String message() default "Duplicate address types are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

