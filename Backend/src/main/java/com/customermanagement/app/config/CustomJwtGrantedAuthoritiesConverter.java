package com.customermanagement.app.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import com.customermanagement.app.entity.Customer;
import com.customermanagement.app.entity.Role;
import com.customermanagement.app.repository.CustomerRespository;
/**
 * This converter is responsible for extracting authorities (roles) from a 
 * JWT (JSON Web Token) and returning them as a collection of GrantedAuthority instances.
 */
public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

	
	@Autowired
	private CustomerRespository customerRepository;
	
	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {
		
		String email = jwt.getSubject();
		Customer customer = customerRepository.findByEmail(email).get();
		Set<Role> roles = customer.getRoles();
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Role role: roles) {
			SimpleGrantedAuthority sga=new SimpleGrantedAuthority(role.getRoleName());
			authorities.add(sga);
		}
		return authorities;
	}

}
