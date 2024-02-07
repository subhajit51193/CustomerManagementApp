package com.customermanagement.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.CustomerNotFoundException;
import com.customermanagement.app.model.CustomerDTO;
import com.customermanagement.app.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * This API used by customers to get their personal details
	 * 
	 * @param: HttpServletRequest -> To get attribute id previously set during token authentication
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@GetMapping("/myDetails")
	public ResponseEntity<CustomerDTO> getCustomerById(HttpServletRequest request) throws CustomerNotFoundException{
		
		String id = request.getAttribute("id").toString();
		CustomerDTO customer = customerService.getCustomerDetailById(id);
		return new ResponseEntity<CustomerDTO>(customer,HttpStatus.OK);
	}
	
	/**
	 * This API used by customers to update their personal details
	 * 
	 * @param: HttpServletRequest -> To get attribute id previously set during token authentication
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@PatchMapping("/update")
	public ResponseEntity<CustomerDTO> updateCustomerDetails(HttpServletRequest request,@RequestBody Customer customer) throws CustomerNotFoundException{
		
		String id = request.getAttribute("id").toString();
		CustomerDTO updated = customerService.updateCustomerDetails(id, customer);
		return new ResponseEntity<CustomerDTO>(updated,HttpStatus.ACCEPTED);
	}
	
}
