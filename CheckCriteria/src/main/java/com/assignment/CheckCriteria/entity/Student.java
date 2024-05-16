package com.assignment.CheckCriteria.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="roll_no")
    private int rollNo;

    @Column(name="std_name")
    private String studentName;

    @Column(name="science")
    private int science;
    
    @Column(name="maths")
    private int maths;

    @Column(name="english")
    private int english;
    
    @Column(name="computer")
    private int computer;
    
    @Column(name="eligible")
    private int eligible;

    public Student() {
    	
    }
	public Student(int rollNo, String studentName, int science, int maths, int english, int computer,
			int eligible) {
		super();
		this.rollNo = rollNo;
		this.studentName = studentName;
		this.science = science;
		this.maths = maths;
		this.english = english;
		this.computer = computer;
		this.eligible = eligible;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getScience() {
		return science;
	}
	public void setScience(int science) {
		this.science = science;
	}
	public int getMaths() {
		return maths;
	}
	public void setMaths(int maths) {
		this.maths = maths;
	}
	public int getEnglish() {
		return english;
	}
	public void setEnglish(int english) {
		this.english = english;
	}
	public int getComputer() {
		return computer;
	}
	public void setComputer(int computer) {
		this.computer = computer;
	}
	public int getEligible() {
		return eligible;
	}
	public void setEligible(int eligible) {
		this.eligible = eligible;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", rollNo=" + rollNo + ", studentName=" + studentName + ", science=" + science
				+ ", maths=" + maths + ", english=" + english + ", computer=" + computer + ", eligible=" + eligible
				+ "]";
	}
    
    
    


}


