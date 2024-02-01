package com.customermanagement.app.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.entity.Role;
import com.customermanagement.app.exception.CustomerNotFoundException;
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
            Set<GrantedAuthority> authorities = new HashSet<>();
            Set<Role> roles = customer.getRoles();
            
            for (Role role : roles) {
            	
            	SimpleGrantedAuthority sga=new SimpleGrantedAuthority(role.getRoleName());
            	System.out.println("siga "+sga);
            	authorities.add(sga);
            }
            System.out.println("granted authorities "+authorities);
			return new User(customer.getEmail(),customer.getPassword(),authorities);
		}
	}

	@Override
	public Customer getCustomerDetailById(String id) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerRespository.findById(id);
		if (opt.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		Customer customer = opt.get();
		return customer;
	}

	@Override
	public Customer updateCustomerDetails(String id,Customer customer) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerRespository.findById(id);
		if (opt.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		Customer foundCustomer = opt.get();
		
		if (customer.getFirstName() != null) {
			foundCustomer.setFirstName(customer.getFirstName());
		}
		if (customer.getLastName() != null) {
			foundCustomer.setLastName(customer.getLastName());
		}
		if (customer.getAddress() != null) {
			foundCustomer.setAddress(customer.getAddress());
		}
		if (customer.getCity() != null) {
			foundCustomer.setCity(customer.getCity());
		}
		if (customer.getPhone() != null) {
			foundCustomer.setPhone(customer.getPhone());
		}
		if (customer.getState() != null) {
			foundCustomer.setState(customer.getState());
		}
	}

}
