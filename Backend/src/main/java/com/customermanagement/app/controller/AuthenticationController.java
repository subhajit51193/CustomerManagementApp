package com.customermanagement.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.UserDetailsNotValidException;
import com.customermanagement.app.model.AuthenticationResponse;
import com.customermanagement.app.service.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	/**
	 * Registers a new users based on provided information
	 * 
	 * @param: User -> User object with details
	 * @param: HttpServeletResponse -> to add cookie details
	 * 
	 * @return: ResponseEntity -> Containing customized authentication response
	 * 
	 * @throws: UserDetailsNotValidException -> if details not valid
	 */
	@PostMapping("/api/register")
	public ResponseEntity<AuthenticationResponse> registerUserHandler(@RequestBody Customer customer,HttpServletResponse response) throws UserDetailsNotValidException{
		
		AuthenticationResponse authenticationResponse = authenticationService.registerUser(customer, response);
		return new ResponseEntity<AuthenticationResponse>(authenticationResponse,HttpStatus.OK);
	}
	
	/**
	 * Authenticates a new users based on provided information
	 * 
	 * @param: Request -> Authentication request containing email and password
	 * @param: HttpServeletResponse -> to add cookie details
	 * 
	 * @return: ResponseEntity -> Containing customized authentication response
	 */
	@PostMapping("/api/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticateUserHandler(Authentication authentication,HttpServletResponse response){
		
		AuthenticationResponse authenticationResponse = authenticationService.authenticateUser(authentication, response);
		return new ResponseEntity<AuthenticationResponse>(authenticationResponse,HttpStatus.OK);
	}
	
	@GetMapping("/api/home")
	public String homePage(Authentication authentication) throws Exception {
	     return "Welcome to the home page!" + authentication.getName();
	}
}
