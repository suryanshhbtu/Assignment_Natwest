package com.assignment.CheckCriteria;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assignment.CheckCriteria.dao.StudentDAOImpl;
import com.assignment.CheckCriteria.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class StudentDAOImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private StudentDAOImpl studentDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllStudent() {
        // Prepare mock student data
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student(100101, "Vivek Sharma", 86, 89, 78, 92, "YES"));
        mockStudents.add(new Student(100102, "John Doe", 75, 80, 85, 90, "YES"));

        TypedQuery<Student> mockQuery = mock(TypedQuery.class);
        when(mockQuery.getResultList()).thenReturn(mockStudents);
        when(entityManager.createQuery("Select s from Student s", Student.class)).thenReturn(mockQuery);

        List<Student> result = studentDAO.findAllStudent();

        assertEquals(2, result.size());
     
    }
}
