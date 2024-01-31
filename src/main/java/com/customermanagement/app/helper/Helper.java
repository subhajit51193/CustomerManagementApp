package com.customermanagement.app.helper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.model.AuthenticationResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This class contains some repetitive logics which are used in the application 
 */
@Component
public class Helper {

	/*
	 * Creates a random string for Id
	 */
	public String createRandomStringId() {
		
		String newStringId = UUID.randomUUID().toString();
		return newStringId;
	}
	
	/*
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
				.phone(customer.getState())
				.address(customer.getAddress())
				.state(customer.getStreet())
				.token(accessToken)
				.build();
		
		return authenticationResponse;
	}
}
