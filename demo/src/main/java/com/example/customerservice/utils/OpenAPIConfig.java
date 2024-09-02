package com.example.customerservice.utils;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi customerApi() {
        return GroupedOpenApi.builder()
            .pathsToMatch("/api/customers/**")
            .build();
    }

    @Bean
    public GroupedOpenApi customerCreateApi() {
        return GroupedOpenApi.builder()
            .pathsToMatch("/api/customers")
           // .pathsToExclude("/api/customers/{id}") // Exclude specific paths if needed
            .build();
    }

    @Bean
    public GroupedOpenApi customerByIdApi() {
        return GroupedOpenApi.builder()
           // .group("customer-by-id")
            .pathsToMatch("/api/customers/{id}")
            .build();
    }
}

