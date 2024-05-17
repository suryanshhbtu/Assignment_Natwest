package com.assignment.CheckCriteria;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title = "Student OPEN API",
				version = "1.0.0",
				description = "Oye Oyes"),
		servers=@Server(
				url="http://localhost:8080",
				description="Student OPEN API url")
		
		)
public class CheckCriteriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckCriteriaApplication.class, args);
	}
	
	
	
	

}
