package com.assignment.CheckCriteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.assignment.CheckCriteria.dao.StudentDAO;
import com.assignment.CheckCriteria.dao.StudentDAOImpl;
import com.assignment.CheckCriteria.entity.Student;

@SpringBootApplication
public class CheckCriteriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckCriteriaApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner->{
			
			Student std = new Student(1001,"Suryansh",56,55,55,44,0);
			studentDAO.save(std);
			System.out.println("Hello "+std.toString());
			
		};
	}
	
	

}
