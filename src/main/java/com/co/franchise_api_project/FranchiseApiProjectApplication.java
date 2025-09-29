package com.co.franchise_api_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.co.franchise_api_project.*")
public class FranchiseApiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FranchiseApiProjectApplication.class, args);
	}

}
