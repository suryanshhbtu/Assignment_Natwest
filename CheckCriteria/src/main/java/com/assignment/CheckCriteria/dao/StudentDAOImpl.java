package com.assignment.CheckCriteria.dao;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.CheckCriteria.entity.Student;

import jakarta.persistence.EntityManager;


@Component
public class StudentDAOImpl implements StudentDAO{

	private EntityManager entityManager;
	
	public StudentDAOImpl(EntityManager en) {
		entityManager = en;
	}

	@Override
	@Transactional
	public void save(Student student) {
			entityManager.persist(student);		
	}
	

	
}
