package com.assignment.CheckCriteria;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.CheckCriteria.controller.RestController;
import com.assignment.CheckCriteria.dao.StudentDAO;
import com.assignment.CheckCriteria.entity.Student;
import com.opencsv.exceptions.CsvException;

public class RestControllerTest {

    @Mock
    private StudentDAO studentDAO;

    @InjectMocks
    private RestController restController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testParseCSV() throws IOException, CsvException {
        // Prepare test data
        List<MultipartFile> files = new ArrayList<>();
        String csvData = "100101,Vivek Sharma,86,89,78,92,ToBeChecked\n" +
                         "100102,John Doe,75,80,85,90,ToBeChecked\n";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        MultipartFile multipartFile = new MockMultipartFile("test.csv", inputStream);
        files.add(multipartFile);

        // Mock behavior of studentDAO.saveAll()
        when(studentDAO.saveAll(Mockito.anyList())).thenReturn(getMockStudentList());

        // Call the method under test
        List<Student> result = (List<Student>) restController.parseCSV(files);
        // Verify the result
        assertEquals(2, result.size());
        // Add more assertions as needed

        // Verify that studentDAO.saveAll() was called with correct arguments
        verify(studentDAO).saveAll(Mockito.anyList());
    }

    private String getMockStudentList() {
        // Prepare mock student data
        List<Student> students = new ArrayList<>();
        students.add(new Student(100101, "Vivek Sharma", 86, 89, 78, 92, "YES"));
        students.add(new Student(100102, "John Doe", 75, 80, 85, 90, "YES"));
        return students.toString();
    }

}


