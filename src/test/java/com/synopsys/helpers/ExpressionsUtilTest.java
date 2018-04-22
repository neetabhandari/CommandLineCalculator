/**
 * 
 */
package com.synopsys.helpers;

import org.junit.Test;


import junit.framework.Assert;

/**
 * Test class to test ExpressionsUtil
 * 
 * @author Neeta
 *
 */
public class ExpressionsUtilTest {
	
	private static final String EXPR_1 = "add(1, 2)";
	private static final String EXPR_2 = "add(1, mult(2, 3))";
	private static final String EXPR_3 = "mult(add(2, 2), div(9, 3))";
	private static final String EXPR_4 = "let(a, 5, add(a, a))";
	private static final String EXPR_5 = "let(a, 5, let(b, mult(a, 10), add(b, a)))";
	private static final String EXPR_6 = "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))";
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate1() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_1.replaceAll("\\s", ""));
		int expectedResult = 3;
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate2() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_2.replaceAll("\\s", ""));
		int expectedResult = 7;
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate3() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_3.replaceAll("\\s", ""));
		int expectedResult = 12;
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate4() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_4.replaceAll("\\s", ""));
		int expectedResult = 10;
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate5() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_5.replaceAll("\\s", ""));
		int expectedResult = 55;
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	/**
	 * Test calculate
	 * @throws Exception 
	 */
	@Test
	public void testCalculate6() throws Exception {
		int actualResult = ExpressionsUtil.calculate(EXPR_6.replaceAll("\\s", ""));
		int expectedResult = 40;
		Assert.assertEquals(expectedResult, actualResult);
	}

}
