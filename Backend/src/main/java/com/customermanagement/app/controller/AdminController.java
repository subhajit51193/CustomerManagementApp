package com.customermanagement.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.CustomerNotFoundException;
import com.customermanagement.app.model.CustomerDTO;
import com.customermanagement.app.service.CustomerService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/home")
	public String homePage(Authentication authentication) throws Exception {
	     return "Welcome to the home page! Captain!!!" + authentication.getName();
	}
	
	/**
	 * This API used by customers with Admin_role to get any customer's personal details
	 * 
	 * @param: String -> CustomerId of whose details is required
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable String id) throws CustomerNotFoundException{
		
		CustomerDTO dto = customerService.getCustomerDetailById(id);
		return new ResponseEntity<CustomerDTO>(dto,HttpStatus.OK);
	}
	
	/**
	 * This API used by customers with admin_role to get all customer details
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@GetMapping("/allCustomers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
	
		List<CustomerDTO> list = customerService.getAllCustomers();
		return new ResponseEntity<List<CustomerDTO>>(list,HttpStatus.OK);
	}
	
	/**
	 * This API used by customers with Admin_role to update any customer's personal details
	 * 
	 * @param: String -> CustomerId of whose details is required
	 * @param: Customer -> Customer object containing details to be updated
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@PatchMapping("/update/{id}")
	public ResponseEntity<CustomerDTO> updateCustomerDetails(@PathVariable String id,@RequestBody Customer customer) throws CustomerNotFoundException{
		
		CustomerDTO dto = customerService.updateCustomerDetails(id, customer);
		return new ResponseEntity<CustomerDTO>(dto,HttpStatus.ACCEPTED);
	}
	
	/**
	 * This API used by customers with Admin_role to delete any customer details
	 * 
	 * @param: String -> CustomerId of whose details is required to be deleted
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable String id) throws CustomerNotFoundException{
		
		String res = customerService.deleteCustomer(id);
		return new ResponseEntity<String>(res,HttpStatus.ACCEPTED);
	}
	
	/**
	 * This API used by customers with Admin_role to get details with pagination and sorting
	 * 
	 * @param: int -> No of pages
	 * @param: int -> size of data
	 * @param: String -> sortBy parameter
	 * 
	 * @return: ResponseEntity -> Containing customized response
	 */
	@GetMapping("/allCustom")
	public ResponseEntity<List<CustomerDTO>> getAllCustomersCustomHandler(@RequestParam int page,@RequestParam int size,@RequestParam String sortBy){
		List<CustomerDTO> list = customerService.getAllCustomersSorted(page, size, sortBy);
		return new ResponseEntity<List<CustomerDTO>>(list,HttpStatus.OK);
	}
}
