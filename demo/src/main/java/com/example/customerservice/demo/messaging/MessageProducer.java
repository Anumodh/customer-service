package com.example.customerservice.demo.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.customerservice.demo.dao.dto.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageProducer {

    //TODO: read from properties file
    private static final String TOPIC = "customers";


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Customer customer) {
        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson;
        try {
            customerJson = objectMapper.writeValueAsString(customer);
            kafkaTemplate.send(TOPIC, customerJson);
        } catch (JsonProcessingException e) {
          
            e.printStackTrace();
        }
        
    }

}
