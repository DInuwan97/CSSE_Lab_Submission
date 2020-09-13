package com.hackerthon.service;

import org.xml.sax.SAXException;
import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.DriverManager;
import javax.xml.parsers.ParserConfigurationException;
import java.sql.PreparedStatement;
import javax.xml.xpath.XPathExpressionException;
import com.hackerthon.common.UtilTransform;
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
import com.hackerthon.common.UtilC;

public class GetEmpService extends UtilC {

	private final ArrayList<Employee> el = new ArrayList<Employee>();

	private static Connection c;

	private static Statement s;

	private PreparedStatement ps;

	public GetEmpService() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		} 
	}

	public void employeesFromXML() {

		try {
			int s = UtilTransform.XMLXPATHS().size();
			for (int i = 0; i < s; i++) {
				Map<String, String> l = UtilTransform.XMLXPATHS().get(i);
				
				Employee emp = Employee.getInstance();
				
				emp.setEmployeeId(l.get("XpathEmployeeIDKey"));
				emp.setFullName(l.get("XpathEmployeeNameKey"));
				emp.setAddress(l.get("XpathEmployeeAddressKey"));
				emp.setFacultyName(l.get("XpathFacultyNameKey"));
				emp.setDepartment(l.get("XpathDepartmentKey"));
				emp.setDepartment(l.get("XpathDesignationKey"));
				el.add(emp);
				System.out.println(emp.toString() + "\n");
			}
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}

	public void employeeTableCreate() {
		try {
			s = c.createStatement();
			s.executeUpdate(UtilQ.Q("q2"));
			s.executeUpdate(UtilQ.Q("q1"));
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}

	public void employessAdd() {
		try {
			ps = c.prepareStatement(UtilQ.Q("q3"));
			c.setAutoCommit(false);
			for(int i = 0; i < el.size(); i++){
				Employee e = el.get(i);
				ps.setString(1, e.getEmployeeId());
				ps.setString(2, e.getFullName());
				ps.setString(3, e.getAddress());
				ps.setString(4, e.getFacultyName());
				ps.setString(5, e.getDepartment());
				ps.setString(6, e.getDesignation());
				ps.addBatch();
			}
			ps.executeBatch();
			c.commit();
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}

	public void employeeGetById(String eid) {

		Employee e = Employee.getInstance();
		
		try {
			ps = c.prepareStatement(UtilQ.Q("q4"));
			ps.setString(1, eid);
			ResultSet R = ps.executeQuery();
			while (R.next()) {
				e.setEmployeeId(R.getString(1));
				e.setFullName(R.getString(2));
				e.setAddress(R.getString(3));
				e.setFacultyName(R.getString(4));
				e.setDepartment(R.getString(5));
				e.setDesignation(R.getString(6));
			}
			ArrayList<Employee> l = new ArrayList<Employee>();
			l.add(e);
			employeeOutput(l);
		} catch (Exception ex) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}

	public void employeeDelete(String eid) {

		try {
			ps = c.prepareStatement(UtilQ.Q("q6"));
			ps.setString(1, eid);
			ps.executeUpdate();
		} catch (Exception e) {
			// e.printStackTrace();
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}

	public void employeeDisplay() {

		ArrayList<Employee> l = new ArrayList<Employee>();
		try {
			ps = c.prepareStatement(UtilQ.Q("q5"));
			ResultSet r = ps.executeQuery();
			while (r.next()) {
				Employee e = Employee.getInstance();
				e.setEmployeeId(r.getString(1));
				e.setFullName(r.getString(2));
				e.setAddress(r.getString(3));
				e.setFacultyName(r.getString(4));
				e.setDepartment(r.getString(5));
				e.setDesignation(r.getString(6));
				l.add(e);
			}
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
		employeeOutput(l);
	}
	
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
