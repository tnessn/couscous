/**
 * 
 */
package com.github.tnessn.couscous.lang.util;

import java.lang.reflect.Field;

// TODO: Auto-generated Javadoc
/**
 * The Class EnumUtil.
 * @author huangjinfeng
 */
public class EnumUtil {
	
	/**
	 * Contain code.
	 *
	 * @param clazz 枚举class
	 * @param code the code
	 * @return true, if successful
	 */
	public static boolean containCode(Class<?> clazz,String code) { 
		boolean flag=false;
		Field field = null;
		try {
			field = clazz.getDeclaredField("code");
		} catch (NoSuchFieldException | SecurityException e1) {
			throw new RuntimeException(e1);
		}
		field.setAccessible(true); 
		for(Object obj:clazz.getEnumConstants()) {
			try {
				if(field.get(obj).toString().equals(code)){
					flag=true;
					break;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return flag;
	}

}
