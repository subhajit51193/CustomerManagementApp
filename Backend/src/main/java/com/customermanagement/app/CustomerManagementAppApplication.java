package com.customermanagement.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.customermanagement.app.config.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class CustomerManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementAppApplication.class, args);
	}

}
