package com.store_management_tool.management_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ManagementToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementToolApplication.class, args);
	}

}
