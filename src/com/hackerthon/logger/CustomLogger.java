package com.hackerthon.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomLogger {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public void writeLog(Level level, String msg) {
		logger.log(level, msg);
	}
	
	
}
