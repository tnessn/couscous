package com.github.tnessn.couscous.lang.util;

import java.lang.reflect.Field;


// TODO: Auto-generated Javadoc
/**
 * 反射工具.
 *
 * @author huangjinfeng
 */
public class ReflectHelper {
	
	/**
	 * 获取obj对象fieldName的Field.
	 *
	 * @param obj the obj
	 * @param fieldName the field name
	 * @return the field by field name
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	/**
	 * 获取obj对象fieldName的属性值.
	 *
	 * @param obj the obj
	 * @param fieldName the field name
	 * @return the value by field name
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			if (field.isAccessible()) {
				value = field.get(obj);
			} else {
				field.setAccessible(true);
				value = field.get(obj);
				field.setAccessible(false);
			}
		}
		return value;
	}

	
	/**
	 * 设置obj对象fieldName的属性值 .
	 *
	 * @param obj the obj
	 * @param fieldName the field name
	 * @param value the value
	 * @throws SecurityException the security exception
	 * @throws NoSuchFieldException the no such field exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
}
