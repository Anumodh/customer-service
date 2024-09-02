package com.example.customerservice.service;

import com.example.customerservice.demo.dao.CustomerRepository;
import com.example.customerservice.demo.dao.dto.Customer;
import com.example.customerservice.demo.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCustomer_Success() throws JsonProcessingException {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(objectMapper.writeValueAsString(any(Customer.class))).thenReturn("{\"firstName\":\"John\"}");

        // Act
        Customer result = customerService.saveCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
    }

   // @Test
    void saveCustomer_JsonProcessingException() throws JsonProcessingException {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(objectMapper.writeValueAsString(any(Customer.class))).thenThrow(new JsonProcessingException("JSON error") {});

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.saveCustomer(customer));
        assertEquals("failed to parse JSON", exception.getMessage());
        verify(customerRepository, times(1)).save(any(Customer.class));
        verify(kafkaTemplate, never()).send(anyString(), anyString());
    }

    @Test
    void sendMessage_KafkaExceptionHandling() {
        // Arrange
        String customerJson = "{\"firstName\":\"John\"}";

        doThrow(new RuntimeException("Kafka send failure")).when(kafkaTemplate).send(anyString(), anyString());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.sendMessage(customerJson));
        assertEquals("Kafka send failure", exception.getMessage());
        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
    }

    @Test
    void getCustomerById_ReturnsCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setFirstName("John");
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Act
        Customer result = customerService.getCustomerById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(customerRepository, times(1)).findById(anyLong());
    }

}

