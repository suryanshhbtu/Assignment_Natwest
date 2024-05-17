package com.assignment.CheckCriteria.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.assignment.CheckCriteria.dao.StudentDAO;
import com.assignment.CheckCriteria.entity.EligibilityConstants;
import com.assignment.CheckCriteria.entity.Student;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@Tag(name="RestController", description = "Rest Endpoints")
public class RestController {


	private static final Logger LOG = (Logger) LogManager.getLogger(RestController.class);
	
	@Autowired
	StudentDAO studentDAO;

	 @Autowired
	    private TaskExecutor taskExecutor;
	
	@Operation(
			summary = "POST Mapping For CSV File",
			description = "Upload multiple CSV Files Over it"
			)
	    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<List<Student>> parseCSV(@RequestPart("files") List<MultipartFile> files) throws IOException, CsvException {
	        List<Student> studentList = new ArrayList<>();
	        CountDownLatch latch = new CountDownLatch(files.size());

	        for (MultipartFile file : files) {
	            taskExecutor.execute(() -> {
	                try {
	                    parseCSVFile(file, studentList);
	                } finally {
	                    latch.countDown();
	                }
	            });
	        }

	        try {
	            latch.await();
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            e.printStackTrace(); // Handle exception appropriately
	        }

	        // Save to database
	        studentDAO.saveAll(studentList);

	        return ResponseEntity.ok(studentList);
	}
	

    private void parseCSVFile(MultipartFile file, List<Student> studentList) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            // Parse CSV data
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            List<String[]> rows = csvReader.readAll();

            for (int i = 1; i < rows.size(); i++) {
                String[] curr = rows.get(i);
                int rollNo = Integer.parseInt(curr[0]);
                String name = curr[1];
                int science = Integer.parseInt(curr[2]);
                int maths = Integer.parseInt(curr[3]);
                int english = Integer.parseInt(curr[4]);
                int computer = Integer.parseInt(curr[5]);
                String eligible = "NO";

                // Check eligibility criteria
                if (science > EligibilityConstants.SCIENCE &&
                        maths > EligibilityConstants.MATHS &&
                        computer > EligibilityConstants.COMPUTER &&
                        english > EligibilityConstants.ENGLISH)
                    eligible = "YES";

                Student student = new Student(rollNo, name, science, maths, english, computer, eligible);
                synchronized(studentList) {
                    studentList.add(student);
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

	@Operation(
			summary = "GET Mapping For CSV File",
			description = "Download CSV Files Over it"
			)
	@GetMapping(value = "/getAsCsv", produces = "text/csv")
	public ResponseEntity<byte[]> getCSV() throws IOException {

		List<Student> list = studentDAO.findAllStudent();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		   try (CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(out))) {
	            // Write headers
	            csvWriter.writeNext(new String[]{"roll number","student name", "science", "maths","English","computer","Eligible"});
	            // Write data
	            for (Student student : list) {	            	
	                csvWriter.writeNext(new String[]{
	                		student.getRollNo()+"",
	                		student.getStudentName()+"",
	    	            	student.getScience()+"",
	    	            	student.getMaths()+"",
	    	            	student.getEnglish()+"",
	    	            	student.getComputer()+"",
	    	            	student.getEligible()		
	                });
	            }
	            // Write closing message
	            } catch (IOException e) {
	            e.printStackTrace(); // Handle exception appropriately
	        }
		   
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		headers.setContentDispositionFormData("filename", "students.csv");

		return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
		   
		   }


	@Operation(
			summary = "GET Mapping For Stdenf Via Roll Number",
			description = "Get Eligibility via Roll No."
			)
	@GetMapping(value = "/eligible/{rollNo}")
	public String isEligible(@PathVariable int rollNo) {
		Student std = studentDAO.findStudentByRollNo(rollNo);
		if(std == null) return "NA";
		
		return std.getEligible();
	}
	
	
	
	@Operation(
			summary = "POST Mapping For Updating Eligibility Criteria",
			description = "Pass the new eligibility criteria as request parameter"
			)
	@PostMapping(value = "/eligibleCriteria")
	public String updateParameters(@RequestParam int science, @RequestParam int maths, @RequestParam int computer, @RequestParam int english) {
		if(science != 0) {
			EligibilityConstants.SCIENCE = science;
		}
		if(maths != 0) {
			EligibilityConstants.MATHS = maths;
		}
		if(computer != 0) {
			EligibilityConstants.COMPUTER = computer;
		}
		if(english != 0) {
			EligibilityConstants.ENGLISH = english;
		}
		LOG.info("Updated Eligibility Criteria");
		return "Success, Science:  "+EligibilityConstants.SCIENCE+", Maths: "+EligibilityConstants.MATHS+", Computer: "+EligibilityConstants.COMPUTER+", English: "+EligibilityConstants.ENGLISH;
	}
}
