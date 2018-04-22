/**
 * 
 */
package com.synopsys.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.synopsys.helpers.ExpressionsUtil;

/**
 * Public main class for Command Line Calculator
 * 
 * @author Neeta
 *
 */
public class Calculator {
	
	final static Logger logger = Logger.getLogger(Calculator.class);
		
	/**
	 * Main method of command line calculator. Prints the value of expression passed as argument.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		logger.info(String.format("%s - main started", Calculator.class.getName()));
		if (ArrayUtils.isEmpty(args)) {
			logger.error("No argument passed, pass an argument");
			return;
		}
		String expr = args[0];
		logger.info(String.format("Command line argument : %s", args[0]));
		try {
			int result = ExpressionsUtil.calculate(expr.replaceAll("\\s", ""));
			System.out.println(result);
		} catch(Exception ex) {
			logger.error(ex.getMessage());
			ex.printStackTrace();
		}
		
		logger.info(String.format("%s - main ended", Calculator.class.getName()));
	}
	
}
