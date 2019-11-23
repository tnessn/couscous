package com.github.tnessn.couscous.lang.validate;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidationUtilTest.
 *
 * @author huangjinfeng
 * @date:2018-05-25 
 */
public class ValidationUtilTest {

	/**
	 * Test.
	 */
	@Test
	public void test() {
		ParamDto param=new ParamDto();
		param.setPayTime("ii");
		ValidationUtil.validate(param,Group2.class);
	}

}
