package com.hackerthon.main;

import java.util.concurrent.AbstractExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.hackerthon.common.UtilTransform;
import com.hackerthon.logger.CustomLogger;
import com.hackerthon.service.GetEmpService;

public class ExecuteMain {
	
	public static final Logger log = Logger.getLogger(AbstractExecutorService.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		GetEmpService employeeService = GetEmpService.getInstance();
		CustomLogger logger = new CustomLogger();
		try {
			UtilTransform.requestTransform();
			employeeService.employeesFromXML();
			employeeService.employeeTableCreate();
			employeeService.employessAdd();

			employeeService.employeeDisplay();
			
		}catch(NumberFormatException e) {
			logger.writeLog(Level.SEVERE, e.toString());
		}catch(NullPointerException e) {
			log.log(null, null, e.getMessage());
		}catch (Exception e) {		
			logger.writeLog(Level.SEVERE, e.toString());
		}

	}

}
