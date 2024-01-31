package com.customermanagement.app.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.customermanagement.app.helper.UrlServiceHelper;
import com.customermanagement.app.service.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerEligibilityFilter extends OncePerRequestFilter{

	@Autowired
	private UrlServiceHelper urlServiceHelper;
	
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
		
		request.setAttribute("email", email);
		filterChain.doFilter(request, response);
		
	}

}
