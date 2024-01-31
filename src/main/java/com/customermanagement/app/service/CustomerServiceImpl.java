package com.customermanagement.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.repository.CustomerRespository;

@Service
public class CustomerServiceImpl implements CustomerService,UserDetailsService{

	@Autowired
	private CustomerRespository customerRespository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Customer> opt = customerRespository.findByEmail(email);
		
		if (opt.isEmpty()) {
			 throw new UsernameNotFoundException("We can't find an account with the provided email!");
		}
		else {
			Customer customer = opt.get();
			return new User(customer.getEmail(),customer.getPassword(),null);
		}
	}

}
