package com.hackerthon.model;

public class Employee {

	public String employeeId;
	public String fullName;
	public String address;
	public String facultyName;
	public String department;
	public String designation;
	
	private static Employee obj;
	
	private Employee() {}
	
	public static synchronized Employee getInstance() {
		 if (obj==null) 
	            obj = new Employee(); 
	        return obj; 
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	@Override
	public String toString() {
		return "Employee Id=" + employeeId + "\n"
				+ "Full Name=" + fullName + "\n"
				+ "Address=" + address + "\n"
				+ "Faculty Name=" + facultyName + "\n"
				+ "Department=" + department + "\n"
				+ "Designation=" + designation + "";
	}

}