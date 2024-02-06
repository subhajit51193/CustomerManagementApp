package com.customermanagement.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customermanagement.app.entity.Customer;

public interface CustomerRespository extends JpaRepository<Customer, String>{

	public Optional<Customer> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	public List<Customer> findByFirstName(String firstName);
}
