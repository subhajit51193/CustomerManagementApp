package com.customermanagement.app.service;

import java.util.List;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.CustomerNotFoundException;
import com.customermanagement.app.model.CustomerDTO;

public interface CustomerService {

	public CustomerDTO getCustomerDetailById(String id)throws CustomerNotFoundException;
	
	public CustomerDTO updateCustomerDetails(String id,Customer customer)throws CustomerNotFoundException;
	
	public List<CustomerDTO> getAllCustomers();
	
	public String deleteCustomer(String id)throws CustomerNotFoundException;
	
	public List<CustomerDTO>getAllCustomersSorted(int page,int size,String sortBy);
}
