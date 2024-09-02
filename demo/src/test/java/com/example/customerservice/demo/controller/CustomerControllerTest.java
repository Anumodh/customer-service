package com.example.customerservice.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.customerservice.demo.dao.dto.Address;
import com.example.customerservice.demo.dao.dto.Customer;
import com.example.customerservice.demo.model.AddressRequest;
import com.example.customerservice.demo.model.CustomerRequest;
import com.example.customerservice.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void testCreateCustomer() throws Exception {
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setType("Home");
        addressRequest.setAddress1("123 Main St");
        addressRequest.setAddress2("Apt 4B");
        addressRequest.setCity("Springfield");
        addressRequest.setState("IL");
        addressRequest.setZipCode("62704");

        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirstName("John");
        customerRequest.setLastName("Doe");
        customerRequest.setCustomerId("C123");
        customerRequest.setAge(30);
        customerRequest.setSpendingLimit(5000.0);
        customerRequest.setMobileNumber("1234567890");
        customerRequest.setAddress(List.of(addressRequest));

        Address addressEntity = new Address();
        addressEntity.setAddress1("123 Main St");
        addressEntity.setType("home");
        addressEntity.setCity("Springfield");

        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCustomerId("C123");
        customer.setAge(30);
        customer.setSpendingLimit(5000.0);
        customer.setMobileNumber("1234567890");
        customer.setAddress(List.of(addressEntity));

        when(customerService.saveCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.address[0].type").value("home"))
                .andExpect(jsonPath("$.address[0].city").value("Springfield"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jane");
        customer.setLastName("Smith");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"));
    }

    @Test
    void testGetCustomerById_NotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        // Create a mock list of customers to be returned by the service
        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setCustomerId("C123");
        customer1.setAge(30);
        customer1.setSpendingLimit(5000.0);
        customer1.setMobileNumber("1234567890");

        Customer customer2 = new Customer();
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setCustomerId("C124");
        customer2.setAge(28);
        customer2.setSpendingLimit(6000.0);
        customer2.setMobileNumber("0987654321");

        List<Customer> customerList = List.of(customer1, customer2);
        Slice<Customer> customerSlice = new SliceImpl<>(customerList);

        when(customerService.getCustomersByCriteria(anyString(), anyString(), anyString(), any(Pageable.class)))
                .thenReturn(customerSlice);

        mockMvc.perform(get("/api/customers"))
                // .param("name", "")
                // .param("city", "")
                // .param("state", "")
                // .param("page", "0")
                // .param("size", "2"))
                .andExpect(status().isOk());
                // .andExpect(jsonPath("$.content[0].firstName").value("John"))
                // .andExpect(jsonPath("$.content[1].firstName").value("Jane"));
    }

}