package com.customermanagement.app.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@NotNull(message = "First Name can not be null!")
	private String firstName;
	
	@Column(name = "last_name")
	@NotEmpty(message = "Last Name can not be empty!!")
	@NotNull(message = "Last Name can not be null!")
	private String lastName;
	
	@Column(name = "street")
	@NotEmpty(message = "Street Name can not be empty!!")
	@NotNull(message = "Street Name can not be null!")
	private String street;
	
	@Column(name = "address")
	@NotEmpty(message = "Address can not be empty!!")
	@NotNull(message = "Address can not be null!")
	private String address;
	
	@Column(name = "city")
	@NotEmpty(message = "City Name can not be empty!!")
	@NotNull(message = "City Name can not be null!")
	private String city;
	
	@Column(name = "state")
	@NotEmpty(message = "State Name can not be empty!!")
	@NotNull(message = "State Name can not be null!")
	private String state;
	
	@Column(name = "phone")
	@NotEmpty(message = "Phone Number can not be empty!!")
	@NotNull(message = "Phone Number can not be null!")
	@Size(min = 10,max = 10,message = "Number must be of 10 digits!!")
	private String phone;
	
	@Column(name = "email",unique = true)
	@Email(message  = "Please provide valid Email Id")
	private String email;
	
	@Column(name = "password")
	@NotEmpty(message = "Password can not be empty!!")
	@NotNull(message = "Password can not be null!")
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "customer_roles",
			joinColumns = @JoinColumn(name = "customer_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toSet());
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
