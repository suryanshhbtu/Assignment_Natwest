package com.assignment.CheckCriteria.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.CheckCriteria.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@Component
public class StudentDAOImpl implements StudentDAO{

	private static final Logger LOG = (Logger) LogManager.getLogger(StudentDAOImpl.class);
	private EntityManager entityManager;
	
	public StudentDAOImpl(EntityManager en) {
		LOG.info("Entity Manager Injected");
		entityManager = en;
	}

	@Override
	@Transactional
	public void save(Student student) {
			entityManager.persist(student);		
	}

	@Override
	public List<Student> findAllStudent() {
		TypedQuery<Student> query = entityManager.createQuery("Select s from Student s", Student.class);
		LOG.info("Generated Table As List");
		return query.getResultList();
	}

	@Override
	@Transactional
	public void saveAll(List<Student> list) {
		for(Student std : list) {
			entityManager.persist(std);
		}
		LOG.info("Saved All Students present in list");
	}

	@Override
	public Student findStudentByRollNo(int roll) {
		TypedQuery<Student> query = entityManager.createQuery("Select s from Student s where s.rollNo= :rollNo", Student.class);
		query.setParameter("rollNo", roll);

		LOG.info("Finding Student By RollNo. ");
		try{
			Student std = query.getSingleResult();
			return std;
		}catch(Exception e) {
			return null;
		}
	}
	

	
}
