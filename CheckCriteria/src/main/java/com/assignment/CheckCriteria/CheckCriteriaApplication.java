package com.assignment.CheckCriteria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
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
				title = "Student Eligibility CSV Generator",
				version = "1.0.0",
				description = "Upload Multiple CSV, Download Combined CSV along with eligibility column, Get Eligibily Of Specific Student, "
						+ "Change Criteria For Eligibility"),
		servers=@Server(
				url="http://localhost:8080",
				description="Student OPEN API url")
		
		)
public class CheckCriteriaApplication {
	
	private static final Logger LOG = (Logger) LogManager.getLogger(CheckCriteriaApplication.class);
	

	public static void main(String[] args) {
//		LOG.debug("debug Message");
//		LOG.info("info Message");
		SpringApplication.run(CheckCriteriaApplication.class, args);
	}
	
	
	
	

}
