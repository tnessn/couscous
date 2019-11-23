package com.github.tnessn.couscous.lang.util;

// TODO: Auto-generated Javadoc
/**
 * The Class TaxUtils.
 *
 * @author huangjinfeng
 */
public class TaxUtils {
	
	/** The Constant THRESHOLD. */
	private final static double THRESHOLD = 3500D;
	
	/** The Constant RATE. */
	private final static int[] RATE = { 3, 10, 20, 25, 30, 35, 45 };
	
	/** The Constant BORDER. */
	private final static int[] BORDER = { 0, 1500, 4500, 9000, 35000, 55000, 80000 };
	
	/**
	 * Instantiates a new tax utils.
	 */
	private TaxUtils() {}
	
	
	/**
	 * 计算个人所得税.
	 *
	 * @param taxableWage 应税工资
	 * @return 应纳税金额
	 */
	public static double calculate(double taxableWage) {
		double tax = 0;
		double wage = taxableWage - THRESHOLD;
		double diff=0D;
		for (int i = 0; i < RATE.length&&wage>0; i++) {
			if (i<RATE.length-1&&wage > (diff=BORDER[i + 1] - BORDER[i])) {
				tax += RATE[i] *0.01* diff;
				wage -= diff;
			} else {
				tax += wage * RATE[i]*0.01;
				wage=0;
				break;
			}
		}
		return tax;
	}
	
	
	
}
