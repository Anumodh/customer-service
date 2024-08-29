package com.example.customerservice.demo.controller;

// import org.springframework.beans.factory.annotation.Value; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController; 

@RestController
public class CustomerController { 

	// Injecting the value of 'my.website.name' 
	// property from configuration 
//	@Value("${my.website.name}") 
//	private String myWebsiteName; 

	// Handling GET requests at the root path ("/") 
	// and returning a welcome message 
	@RequestMapping(path = "/hello", method = RequestMethod.GET) 
	public String welcome() { 
		// Constructing and returning a welcome message 
		// including the injected website name 
		return "Welcome to Springboot111"; 
	} 

} 
