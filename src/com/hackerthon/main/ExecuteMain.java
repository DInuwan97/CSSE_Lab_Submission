package com.hackerthon.main;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.hackerthon.common.UtilTransform;
import com.hackerthon.service.GetEmpService;

public class ExecuteMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetEmpService employeeService = new GetEmpService();
		try {
			UtilTransform.requestTransform();
			employeeService.employeesFromXML();;
			employeeService.employeeTableCreate();
			employeeService.employessAdd();

			employeeService.employeeDisplay();
		} catch (Exception e) {
		}

	}

}
