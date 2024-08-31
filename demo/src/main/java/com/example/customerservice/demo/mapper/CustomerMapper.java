package com.example.customerservice.demo.mapper;


import com.example.customerservice.demo.model.*;
import com.example.customerservice.demo.dao.dto.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setCustomerId(customerRequest.getCustomerId());
        customer.setAge(customerRequest.getAge());
        customer.setSpendingLimit(customerRequest.getSpendingLimit());
        customer.setMobileNumber(customerRequest.getMobileNumber());

        List<Address> addresses = customerRequest.getAddress().stream()
                .map(CustomerMapper::toEntity)
                .collect(Collectors.toList());

        customer.setAddress(addresses);
        addresses.forEach(address -> address.setCustomer(customer));

        return customer;
    }

    private static Address toEntity(AddressRequest addressRequest) {
        Address address = new Address();
        address.setType(addressRequest.getType());
        address.setAddress1(addressRequest.getAddress1());
        address.setAddress2(addressRequest.getAddress2());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipCode(addressRequest.getZipCode());
        return address;
    }
}

