package com.customermanagement.app.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "customer_id")
	private String customerId;
	
	@Column(name = "first_name")
	@NotEmpty(message = "First Name can not be empty!!")
	private String firstName;
	
	
	@Column(name = "last_name")
	@NotEmpty(message = "Last Name can not be empty!!")
	private String lastName;
	
	@Column(name = "street")
	@NotEmpty(message = "Street Name can not be empty!!")
	private String street;
	
	@Column(name = "address")
	@NotEmpty(message = "Address can not be empty!!")
	private String address;
	
	@Column(name = "city")
	@NotEmpty(message = "City can not be empty!!")
	private String city;
	
	@Column(name = "state")
	@NotEmpty(message = "State Name can not be empty!!")
	private String state;
	
	@Column(name = "phone")
	@NotEmpty(message = "Phone Number can not be empty!!")
	private String phone;
	
	@Column(name = "email",unique = true)
	@NotEmpty(message = "EmailID can not be empty!!")
	private String email;
	
	@Column(name = "password")
	@NotEmpty(message = "Password can not be empty!!")
	private String password;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return firstName + " " + lastName;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
}
