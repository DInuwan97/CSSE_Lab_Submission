package com.hackerthon.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.hackerthon.logger.CustomLogger;

import java.io.IOException;
import java.util.Properties;


public class UtilC {

	public static final Properties p = new Properties();

	static {
		try {
			p.load(UtilQ.class.getResourceAsStream("../config/config.properties"));
		} catch (Exception e) {
			CustomLogger logger = new CustomLogger();
			logger.writeLog(Level.SEVERE, e.toString());
		}
	}
}
