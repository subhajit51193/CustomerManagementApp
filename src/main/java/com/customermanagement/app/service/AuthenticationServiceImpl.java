package com.customermanagement.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.exception.UserDetailsNotValidException;
import com.customermanagement.app.helper.Helper;
import com.customermanagement.app.model.AuthenticationResponse;
import com.customermanagement.app.repository.CustomerRespository;

import jakarta.servlet.http.HttpServletResponse;

/*
 * This class contains all the methods implementations related to authentication services
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	@Autowired
	private CustomerRespository customerRespository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private Helper helper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*
	 * Register new user
	 * 
	 * @param: User and Role -> User is registered with associated roles
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return: AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and user information
	 * 
	 * @throws: UserNotValidException -> throws this exception in case invalid details provided by client
	 * 
	 * @throws: UserAlreadyExistsWithSameEmailException -> throws this exception if user with same email already 
	 * exists in database									
	 * 
	 */
	public AuthenticationResponse registerUser(Customer customer,HttpServletResponse response) throws UserDetailsNotValidException {
		
		if (customer == null) {
			
			throw new UserDetailsNotValidException("The User details are not valid !! Please recheck and try again!!!");
		}
		
		// setting all data with customer and save
		customer.setCustomerId(helper.createRandomStringId());
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Customer registeredCustomer = customerRespository.save(customer);
		
		//Creating accessToken and RefreshToken
		String accessToken = jwtTokenService.generateToken(customer.getEmail());
		String refreshToken = jwtTokenService.generateRefreshToken(customer.getEmail());

		//Creating cookie with all the variables and return a response
		AuthenticationResponse authenticationResponse = helper.createCookie(registeredCustomer, accessToken, refreshToken, response);
		
		return authenticationResponse;
	}
	
	/*
	 * Authenticates user using password and email during login
	 * 
	 * @param: Authentication -> basic authentication is used by spring on user given email and password
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return: AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and user information							
	 */
	public AuthenticationResponse authenticateUser(Authentication authentication,HttpServletResponse response) {
		
		//Getting email after basic authentication is done
		String email = authentication.getName();
		Optional<Customer> opt  = customerRespository.findByEmail(email);
		Customer customer = opt.get();
		
		//Creating accessToken and RefreshToken
		String accessToken  = jwtTokenService.generateToken(email);
		String refreshToken = jwtTokenService.generateRefreshToken(customer.getEmail());
		
		//Creating cookie with all the variables and return a response
		AuthenticationResponse authenticationResponse = helper.createCookie(customer, accessToken, refreshToken, response);
		
		return authenticationResponse;
	}
}
