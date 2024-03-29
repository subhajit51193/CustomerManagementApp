package com.customermanagement.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.customermanagement.app.helper.Helper;
import com.customermanagement.app.model.CustomerDTO;
import com.customermanagement.app.repository.CustomerRespository;

@Service
public class CustomerServiceImpl implements CustomerService,UserDetailsService{

	@Autowired
	private CustomerRespository customerRespository;
	
	@Autowired
	private Helper helper;
	
	/**
	 * This method is by spring to loading user details based on provided
	 * email at the time of log in
	 * 
	 * @param: String -> email provided by user
	 * 
	 * @throws: UsernameNotFoundException -> Exception is thrown if no user found
	 * 
	 * @return: UserDetails -> Object with userEmail,password and authorities
	 */
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

	/**
	 * This method provides customer details based on id
	 * 
	 * @param: String -> customerId 
	 * 
	 * @throws: CustomerNotFoundException -> Exception is thrown in case customer
	 * not found with given id
	 * 
	 * @return: CustomerDTO -> Customized object with customer details
	 */
	@Override
	public CustomerDTO getCustomerDetailById(String id) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerRespository.findById(id);
		if (opt.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		Customer customer = opt.get();
		CustomerDTO dto = helper.convertToDto(customer);
		return dto;
	}

	/*
	 * This method updates customer details
	 * 
	 * @param: String -> customerId 
	 * @param: Customer -> Customer object with required details
	 * 
	 * @throws: CustomerNotFoundException -> Exception is thrown in case customer
	 * not found with given id
	 * 
	 * @return: CustomerDTO -> Customized object with customer details
	 */
	@Override
	public CustomerDTO updateCustomerDetails(String id,Customer customer) throws CustomerNotFoundException {
		
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
		Customer updatedCustomer = customerRespository.save(foundCustomer);
		CustomerDTO dto = helper.convertToDto(updatedCustomer);
		return dto;
	}

	/**
	 * This method provides all customer details stored in database
	 * 
	 * @return: List<CustomerDTO> -> List of Customized object with customer details
	 */
	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		List<Customer> list = customerRespository.findAll();
		List<CustomerDTO> dtoList = new ArrayList<>();
		
		for (Customer customer: list) {
			
			CustomerDTO dto = helper.convertToDto(customer);
			dtoList.add(dto);
		}
		return dtoList;
	}

	/**
	 * This method deletes customer details based on id from database
	 * 
	 * @param: String -> customerId 
	 * 
	 * @throws: CustomerNotFoundException -> Exception is thrown in case customer
	 * not found with given id
	 * 
	 * @return: String -> Positive message in case deletion is successful
	 */
	@Override
	public String deleteCustomer(String id) throws CustomerNotFoundException {
		
		Optional<Customer> opt = customerRespository.findById(id);
		if (opt.isEmpty()) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		Customer foundCustomer = opt.get();
		// Remove roles from the customer_roles table
	    foundCustomer.getRoles().clear();
		customerRespository.delete(foundCustomer);
		return "Customer details Deleted";
	}

	
	/**
	 * Get all customers with pagination and sorting
	 * 
	 * @param: int -> pages
	 * @param: int -> size of data
	 * @param: String -> sorting parameter
	 * 
	 * @return: List<Employees> -> returns list of all customers that follow all criteria
	 */
	@Override
	public List<CustomerDTO> getAllCustomersSorted(int page,int size, String sortBy) {
		
		PageRequest pageable = PageRequest.of(page, size,Sort.by(sortBy));
		Page<Customer> customerPage = customerRespository.findAll(pageable);
		List<Customer> list = customerPage.getContent();
		
		List<CustomerDTO> res = new ArrayList<>();
		for (Customer customer: list) {
			CustomerDTO dto = helper.convertToDto(customer);
			res.add(dto);
		}
		return res;
	}

}
