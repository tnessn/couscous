package com.github.tnessn.couscous.lang.util;

import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class ValidCodeUtils.
 *
 * @author huangjinfeng
 */
public class ValidCodeUtils {
	
	/**
	 * 随机生成六位数验证码 .
	 *
	 * @return the random num
	 */
	public static int getRandomNum(){
		 Random r = new Random();
		 return r.nextInt(900000)+100000;
	}

}
