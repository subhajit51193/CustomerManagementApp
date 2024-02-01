package com.customermanagement.app.service;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.CustomerNotFoundException;

public interface CustomerService {

	public Customer getCustomerDetailById(String id)throws CustomerNotFoundException;
	
	public Customer updateCustomerDetails(String id,Customer customer)throws CustomerNotFoundException;
}
