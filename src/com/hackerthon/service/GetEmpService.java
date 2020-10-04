package com.hackerthon.service;

import org.xml.sax.SAXException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.PreparedStatement;
import javax.xml.xpath.XPathExpressionException;
import com.hackerthon.common.UtilTransform;
import com.hackerthon.config.GlobalConstants;
import com.hackerthon.logger.CustomLogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.sql.Statement;
import com.hackerthon.common.UtilQ;
import java.io.IOException;
import com.hackerthon.model.Employee;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.AbstractExecutorService;

import com.hackerthon.common.UtilC;

public class GetEmpService extends UtilC {

	//declaring static variables
	private final ArrayList<Employee> el = new ArrayList<Employee>();
	private static Connection c;
	private static Statement s;
	private PreparedStatement ps;
	private static GetEmpService singleInstance = null; 
	public static final Logger log = Logger.getLogger(AbstractExecutorService.class.getName());

	
	//get db connection
	public GetEmpService() {
		
		CustomLogger logger = new CustomLogger();
		
		try {
			Class.forName(GlobalConstants.DRIVER_NAME);
			c = DriverManager.getConnection(p.getProperty(GlobalConstants.URL), p.getProperty(GlobalConstants.USERNAME),
					p.getProperty(GlobalConstants.PASSWORD));
		}catch (NullPointerException e) {
			logger.writeLog(Level.SEVERE, e.toString());
		} 
		catch (SQLException e) {
			logger.writeLog(Level.SEVERE, e.toString());
		} 
		catch (ClassNotFoundException e) {
			logger.writeLog(Level.SEVERE, e.toString());
		} 
	}
	
	//using singleton
	public static GetEmpService getInstance() 
    { 
        if (singleInstance == null) 
        	singleInstance = new GetEmpService(); 
  
        return singleInstance; 
    } 

	// get employees
	public void employeesFromXML() {

		try {
			int s = UtilTransform.XMLXPATHS().size();
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTransform.XMLXPATHS().get(i);
				
				Employee emp = new Employee();
				
				emp.setEmployeeId(l.get(GlobalConstants.X_PATH_EMPLOYEE_ID_KEY));
				emp.setFullName(l.get(GlobalConstants.X_PATH_EMPLOYEE_NAME_KEY));
				emp.setAddress(l.get(GlobalConstants.X_PATH_EMPLOYEE_ADDRESS_KEY));
				emp.setFacultyName(l.get(GlobalConstants.X_PATH_EMPLOYEE_FACULTY_NAME_KEY));
				emp.setDepartment(l.get(GlobalConstants.X_PATH_EMPLOYEE_DEPARTMENT_KEY));
				emp.setDesignation(l.get(GlobalConstants.X_PATH_EMPLOYEE_DESIGNATION_KEY));
				el.add(emp);
				System.out.println(emp.toString() + "\n");
			}
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
	}

	// create employee table
	public void employeeTableCreate() {
		try {
			s = c.createStatement();
			s.executeUpdate(UtilQ.Q(GlobalConstants.Q2));
			s.executeUpdate(UtilQ.Q(GlobalConstants.Q1));
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
	}

	// add employee
	public void employessAdd() {
		try {
			ps = c.prepareStatement(UtilQ.Q(GlobalConstants.Q3));
			c.setAutoCommit(false);
			for(int i = 0; i < el.size(); i++){
				Employee e = el.get(i);
				ps.setString(GlobalConstants.COLUMN_INDEX_ONE, e.getEmployeeId());
				ps.setString(GlobalConstants.COLUMN_INDEX_TWO, e.getFullName());
				ps.setString(GlobalConstants.COLUMN_INDEX_THREE, e.getAddress());
				ps.setString(GlobalConstants.COLUMN_INDEX_FOUR, e.getFacultyName());
				ps.setString(GlobalConstants.COLUMN_INDEX_FIVE, e.getDepartment());
				ps.setString(GlobalConstants.COLUMN_INDEX_SIX, e.getDesignation());
				ps.addBatch();
			}
			ps.executeBatch();
			c.commit();
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
	}

	// get employee from id
	public void employeeGetById(String eid) {

		Employee e = new Employee();
		
		try {
			ps = c.prepareStatement(UtilQ.Q(GlobalConstants.Q4));
			ps.setString(GlobalConstants.COLUMN_INDEX_ONE, eid);
			ResultSet R = ps.executeQuery();
			while (R.next()) {
				e.setEmployeeId(R.getString(GlobalConstants.COLUMN_INDEX_ONE));
				e.setFullName(R.getString(GlobalConstants.COLUMN_INDEX_TWO));
				e.setAddress(R.getString(GlobalConstants.COLUMN_INDEX_THREE));
				e.setFacultyName(R.getString(GlobalConstants.COLUMN_INDEX_FOUR));
				e.setDepartment(R.getString(GlobalConstants.COLUMN_INDEX_FIVE));
				e.setDesignation(R.getString(GlobalConstants.COLUMN_INDEX_SIX));
			}
			ArrayList<Employee> l = new ArrayList<Employee>();
			l.add(e);
			employeeOutput(l);
		} catch (Exception ex) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
	}

	// delete single employee
	public void employeeDelete(String eid) {

		try {
			ps = c.prepareStatement(UtilQ.Q(GlobalConstants.Q6));
			ps.setString(GlobalConstants.COLUMN_INDEX_ONE, eid);
			ps.executeUpdate();
		} catch (Exception e) {
			// e.printStackTrace();
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
	}

	// print employees
	public void employeeDisplay() {

		ArrayList<Employee> list = new ArrayList<Employee>();
		try {
			ps = c.prepareStatement(UtilQ.Q(GlobalConstants.Q5));
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				Employee e = new Employee();
				e.setEmployeeId(r.getString(GlobalConstants.COLUMN_INDEX_ONE));
				e.setFullName(r.getString(GlobalConstants.COLUMN_INDEX_TWO));
				e.setAddress(r.getString(GlobalConstants.COLUMN_INDEX_THREE));
				e.setFacultyName(r.getString(GlobalConstants.COLUMN_INDEX_FOUR));
				e.setDepartment(r.getString(GlobalConstants.COLUMN_INDEX_FIVE));
				e.setDesignation(r.getString(GlobalConstants.COLUMN_INDEX_SIX));
				list.add(e);
			}
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.WARNING, e.getMessage());
		}
		employeeOutput(list);
	}
	
	// print employee details to console
	public void employeeOutput(ArrayList<Employee> l){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out
				.println("================================================================================================================");
		for(int i = 0; i < l.size(); i++){
			Employee e = l.get(i);
			System.out.println(e.getEmployeeId() + "\t" + e.getFullName() + "\t" + e.getDepartment() + "\t"
					+ e.getDesignation() + "\n");
			System.out
			.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
