package com.customermanagement.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@GetMapping("/home")
	public String homePage(Authentication authentication) throws Exception {
	     return "Welcome to the home page! Captain!!!" + authentication.getName();
	}
}
