/**
 * 
 */
package com.synopsys.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.synopsys.enums.OperatorEnum;

/**
 * ExpressionsUtil helps in manipulating and calculating expression value.
 * 
 * @author Neeta
 *
 */
public class ExpressionsUtil {
	
	final static Logger logger = Logger.getLogger(ExpressionsUtil.class);

	/**
	 * Evaluates and calculates expression to get an integer value
	 * 
	 * @param arg
	 * @return int value
	 * @throws Exception 
	 */
	public static int calculate(String arg) throws Exception {
		logger.debug(String.format("Ärg in calculate : %s", arg));
		
		int result = 0;
		String tempArg = arg;
		while (!StringUtils.isNumeric(tempArg)) {
			Pattern pattern = Pattern.compile("(add|sub|mult|div){1}\\(\\s*[0-9]+,{1}\\s*[0-9]+\\)");
			Matcher matcher = pattern.matcher(tempArg);
			if (matcher.find()) {
				logger.debug(String.format("Match found for  : %s", matcher.group()));
				int temp = getExpressionValue(matcher.group());
				tempArg = matcher.replaceFirst(String.valueOf(temp));
				logger.debug(String.format("Expression now is  : %s", tempArg));
			} else if (OperatorEnum.LET.getValue().equalsIgnoreCase(tempArg.substring(0, 3))) {
				logger.debug("It's a let expression");
				tempArg = String.valueOf(evaluateLet(tempArg));
			} else {
				throw new Exception("Unknown expression");
			}
		}
		result = Integer.parseInt(tempArg);
		logger.debug(String.format("calculate() - result : %d", result));
		return result;
	}

	/**
	 * Evaluates let expression
	 * 
	 * @param expr
	 * @return let result
	 * @throws Exception 
	 */
	public static String evaluateLet(String expr) throws Exception {
		logger.debug(String.format("Ärg in evaluate : %s", expr));

		String letResult = null;
		String argsStr = expr.substring(4, expr.length() - 1);
		String arg1 = StringUtils.substringBefore(argsStr, ",");
		String tempArgsStr = StringUtils.substringAfter(argsStr, ",");
		String tempArg2 = StringUtils.substringBefore(tempArgsStr, ",");
		String arg2 = null;
		String arg3 = null;
		if (StringUtils.isNumeric(tempArg2)) {
			arg2 = tempArg2;
			arg3 = StringUtils.substringAfter(tempArgsStr, ",");
			arg3 = inputVariableValueInLetExpr(arg1, arg2, arg3);
			letResult = String.valueOf(calculate(arg3));
		} else {
			char[] tempArgsArr = tempArgsStr.toCharArray();
			int braceCntr = -1;
			int idx = 0;
			while (braceCntr != 0 && idx < tempArgsArr.length) {
				if ('(' == tempArgsArr[idx]) {
					if (braceCntr == -1)
						braceCntr = 1;
					else
						braceCntr++;
				} else if (')' == tempArgsArr[idx]) {
					braceCntr--;
				}
				idx++;
			}
			String tmpArg2 = tempArgsStr.substring(0, idx);
			String tmpArg3 = tempArgsStr.substring(idx + 1);
			while (!StringUtils.isNumeric(arg2)) {
				arg2 = String.valueOf(calculate(tmpArg2));
			}
			arg3 = inputVariableValueInLetExpr(arg1, arg2, tmpArg3);
			expr = expr.replace(tmpArg2, arg2);
			expr = expr.replace(tmpArg3, arg3);
			logger.debug(String.format("let expression now is : %s", expr));
			if (!StringUtils.isNumeric(expr)) {
				letResult = evaluateLet(expr);
			}
		}
		logger.debug(String.format("let arguments : %s || %s || %s", arg1, arg2, arg3));
		logger.debug(String.format("evaluate() - result : %s", letResult));
		return letResult;
	}

	/**
	 * Replaces arg1 value with arg2 value in arg3 expression
	 * 
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return string after replacement
	 */
	public static String inputVariableValueInLetExpr(String arg1, String arg2, String arg3) {
		String regex1 = "(" + arg1;
		String regex2 = arg1 + ")";
		String replacement1 = "(" + arg2;
		String replacement2 = arg2 + ")";
		arg3 = arg3.replace(regex1, replacement1);
		arg3 = arg3.replace(regex2, replacement2);
		return arg3;
	}

	/**
	 * Manipulates and breaks expression in operation to be performed and arguments and then gets the expression value
	 * 
	 * @param arg
	 * @return int value
	 */
	public static int getExpressionValue(String arg) {
		Pattern pattern = Pattern.compile("add|sub|mult|div|let{1}");
		Matcher matcher = pattern.matcher(arg);
		int end;
		String operation;
		if (matcher.find()) {
			end = matcher.end();
			operation = matcher.group();
		} else {
			return 0;
		}
		String args = arg.substring(end + 1, arg.length() - 1);
		String strArg1 = StringUtils.substringBefore(args, ",").trim();
		String strArg2 = StringUtils.substringAfter(args, ",").trim();

		int arg1 = 0;
		int arg2 = 0;
		if (StringUtils.isNumeric(strArg1)) {
			arg1 = Integer.parseInt(strArg1);
		}
		if (StringUtils.isNumeric(strArg2)) {
			arg2 = Integer.parseInt(strArg2);
		}

		int result = 0;
		result = performOperations(OperatorEnum.fromString(operation), arg1, arg2);
		return result;
	}

	/**
	 * Performs mathematical operations
	 * 
	 * @param operation
	 * @param arg1
	 * @param arg2
	 * @return int value
	 */
	public static int performOperations(OperatorEnum operation, int arg1, int arg2) {
		int result = 0;
		switch (operation) {
		case ADD:
			result = CalcOperationsHelper.add(arg1, arg2);
			break;
		case SUB:
			result = CalcOperationsHelper.sub(arg1, arg2);
			break;
		case MULT:
			result = CalcOperationsHelper.mult(arg1, arg2);
			break;
		case DIV:
			result = CalcOperationsHelper.div(arg1, arg2);
			break;
		case LET:
			break;
		}
		return result;
	}
}
