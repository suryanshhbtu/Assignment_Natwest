package com.assignment.CheckCriteria.dao;

import java.util.List;

import com.assignment.CheckCriteria.entity.Student;

public interface StudentDAO {

	void save(Student student);
	String saveAll(List<Student> list);

	List<Student> findAllStudent();
	Student findStudentByRollNo(int roll);
}
