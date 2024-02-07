package com.customermanagement.app.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.model.AuthenticationResponse;
import com.customermanagement.app.model.CustomerDTO;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This class contains some repetitive logics which are used in the application 
 */
@Component
public class Helper {

	/**
	 * Creates a random string for Id
	 */
	public String createRandomStringId() {
		
		String newStringId = UUID.randomUUID().toString();
		return newStringId;
	}
	
	public CustomerDTO convertToDto(Customer customer) {
		
		CustomerDTO dto = CustomerDTO.builder()
				.id(customer.getCustomerId())
				.address(customer.getAddress())
				.city(customer.getCity())
				.email(customer.getEmail())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.phone(customer.getPhone())
				.state(customer.getState())
				.street(customer.getStreet())
				.roles(customer.getRoles())
				.build();
		return dto;
	}
	
	/**
	 * Creates a cookie and returns authentication response
	 * 
	 * @param Customer -> Customer object with necessary details
	 * @param AccessToken -> generated accessToken
	 * @param RefreshToken -> generated refreshToken
	 * @param HttpServletResponse -> An HTTP response to add authentication tokens
	 * 
	 * @return AuthenticationResponse -> An instance of authenticationResponse containing token
	 * and Customer information
	 */
	public AuthenticationResponse createCookie(Customer customer, String accessToken, String refreshToken, HttpServletResponse response) {
		
		Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setMaxAge(60 * 1000 * 4);
        accessTokenCookie.setDomain("localhost");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setMaxAge(24 * 60 * 60 * 1000 * 7);
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        
        response.addHeader("Authorization", "Bearer " + accessToken);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

		AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
				.email(customer.getEmail())
				.customerId(customer.getCustomerId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.city(customer.getCity())
				.state(customer.getState())
				.phone(customer.getPhone())
				.address(customer.getAddress())
				.street(customer.getStreet())
				.token(accessToken)
				.roles(customer.getRoles())
				.build();
		
		return authenticationResponse;
	}
}
