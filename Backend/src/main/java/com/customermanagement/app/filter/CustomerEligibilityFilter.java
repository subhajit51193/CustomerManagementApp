package com.customermanagement.app.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.helper.UrlServiceHelper;
import com.customermanagement.app.repository.CustomerRespository;
import com.customermanagement.app.service.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Custom filter class which is executed for each HTTP request. User eligibility 
 * can be checked based on request URI and Authorization Header 
 */
@Component
public class CustomerEligibilityFilter extends OncePerRequestFilter{

	@Autowired
	private UrlServiceHelper urlServiceHelper;
	
	@Autowired
	private CustomerRespository customerRespository;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestURI = request.getRequestURI();
		
		if (urlServiceHelper.isPublicUrl(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader("Authorization");
		
		String token = authHeader.split(" ")[1];
		
		String email = jwtTokenService.extractEmailFromToken(token);
		
		Customer customer = customerRespository.findByEmail(email).get();
		String id = customer.getCustomerId();
		
		request.setAttribute("email", email);
		request.setAttribute("id", id);
		filterChain.doFilter(request, response);
		
	}

}
