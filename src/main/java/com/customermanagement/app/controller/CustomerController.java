package com.customermanagement.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.CustomerNotFoundException;
import com.customermanagement.app.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/myDetails")
	public ResponseEntity<Customer> getCustomerById(HttpServletRequest request) throws CustomerNotFoundException{
		
		String id = request.getAttribute("id").toString();
		Customer customer = customerService.getCustomerDetailById(id);
		return new ResponseEntity<Customer>(customer,HttpStatus.OK);
	}
}
