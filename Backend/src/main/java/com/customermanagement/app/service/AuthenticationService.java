package com.customermanagement.app.service;

import org.springframework.security.core.Authentication;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.UserDetailsNotValidException;
import com.customermanagement.app.model.AuthenticationResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

	public AuthenticationResponse registerUser(Customer customer,HttpServletResponse response) throws UserDetailsNotValidException;
	
	public AuthenticationResponse authenticateUser(Authentication authentication,HttpServletResponse response);
}
