package com.hackerthon.common;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;

import com.hackerthon.config.GlobalConstants;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Document;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

public class UtilTransform extends UtilC {
	
	private static final ArrayList<Map<String, String>> l = new ArrayList<Map<String, String>>();

	private static Map<String, String> m = null;

	public static void requestTransform() throws Exception {

		Source x = new StreamSource(new File(GlobalConstants.EMPLOYEE_REQUEST_XML));
		Source s = new StreamSource(new File(GlobalConstants.EMPLOYEE_MODIFIED_XML));
		Result o = new StreamResult(new File(GlobalConstants.EMPLOYEE_RESPONSE_XML));
		TransformerFactory.newInstance().newTransformer(s).transform(x, o);
	}

	public static ArrayList<Map<String, String>> XMLXPATHS() throws Exception {

		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(GlobalConstants.EMPLOYEE_RESPONSE_XML);
		XPath x = XPathFactory.newInstance().newXPath();
		int n = Integer.parseInt((String) x.compile("count(//Employees/Employee)").evaluate(d, XPathConstants.STRING));
		for (int i = 1; i <= n; i++) {
			m = new HashMap<String, String>();
			m.put(GlobalConstants.X_PATH_EMPLOYEE_ID_KEY, (String) x.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(d, XPathConstants.STRING));
			m.put(GlobalConstants.X_PATH_EMPLOYEE_NAME_KEY, (String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
					.evaluate(d, XPathConstants.STRING));
			m.put(GlobalConstants.X_PATH_EMPLOYEE_ADDRESS_KEY,
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(d,
							XPathConstants.STRING));
			m.put(GlobalConstants.X_PATH_EMPLOYEE_FACULTY_NAME_KEY, (String) x.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
					.evaluate(d, XPathConstants.STRING));
			m.put(GlobalConstants.X_PATH_EMPLOYEE_DEPARTMENT_KEY, (String) x.compile("//Employees/Employee[" + i + "]/Department/text()")
					.evaluate(d, XPathConstants.STRING));
			m.put(GlobalConstants.X_PATH_EMPLOYEE_DESIGNATION_KEY, (String) x.compile("//Employees/Employee[" + i + "]/Designation/text()")
					.evaluate(d, XPathConstants.STRING));
			l.add(m);
		}
		return l;
	}
}
