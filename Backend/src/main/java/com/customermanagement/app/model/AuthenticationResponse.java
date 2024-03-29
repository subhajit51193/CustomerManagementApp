package com.customermanagement.app.model;


import java.util.Set;

import com.customermanagement.app.entity.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthenticationResponse {

    private String token;
    private String email;
    private String customerId;
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String phone;
    private Set<Role> roles;
}
