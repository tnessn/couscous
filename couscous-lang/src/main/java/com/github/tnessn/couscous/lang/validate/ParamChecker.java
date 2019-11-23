package com.github.tnessn.couscous.lang.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.github.tnessn.couscous.lang.util.EnumUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ParamChecker.
 *
 * @author huangjinfeng
 */
public class ParamChecker {
	
	/** The Constant EMAIL_REGEX. */
	private final static String  EMAIL_REGEX="[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
	
	/** The error. */
	private List<String> error;
	
	/**
	 * Instantiates a new param checker.
	 */
	public ParamChecker() {
		error=new ArrayList<String>(0);
	}
	
	/**
	 * Assert not blank.
	 *
	 * @param param the param
	 * @param fieldName the field name
	 * @return true, if successful
	 */
	public boolean assertNotBlank (String param,String fieldName) {
		if(StringUtils.isBlank(param)) {
			error.add(fieldName+"未设置");
			return false;
		}
		return true;
	}
	
	
	/**
	 * Assert one not blank.
	 *
	 * @param param1 the param 1
	 * @param param2 the param 2
	 * @param msg the msg
	 * @return true, if successful
	 */
	public boolean assertOneNotBlank (String param1,String param2,String msg) {
		if(StringUtils.isBlank(param1)&&StringUtils.isBlank(param2)) {
			error.add(msg);
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * Assert not blank.
	 *
	 * @param param the param
	 * @param fieldName the field name
	 * @return true, if successful
	 */
	public boolean assertNotBlank(int param, String fieldName) {
		if(param==0) {
			error.add(fieldName+"未设置");
			return false;
		}
		return true;
	}
	
	
	/**
	 * Assert is email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public boolean assertIsEmail(String email) {
		return assertMatchPattern(email, EMAIL_REGEX, "邮箱输入有误");
	}
	
	
	/**
	 * 不符合正则输出异常信息.
	 *
	 * @param param 参数
	 * @param regex 正则表达式
	 * @param msg 提示信息
	 * @return 是否匹配
	 */
	public boolean assertMatchPattern (String param,String regex,String msg) {
		if(StringUtils.isBlank(param)||!Pattern.matches(regex, param)) {
			error.add(msg);
			return false;
		}
		return true;
	}
	
	/**
	 * flag为false时则输出异常信息.
	 *
	 * @param flag  条件
	 * @param fieldName 字段名称
	 * @return 是否匹配
	 */
	public boolean assertConditionTrue (boolean flag,String fieldName) {
		if(!flag) {
			error.add(fieldName+"输入有误");
			return false;
		}
		return true;
	}
	
	/**
	 * 如果在枚举code中不存在参数则输出异常信息.
	 *
	 * @param param 参数
	 * @param clazz 枚举class
	 * @param fieldName 字段名称
	 * @return 是否匹配
	 */
	public boolean assertInEnum(String param,Class<?> clazz,String fieldName) {
		if(!EnumUtil.containCode(clazz, param)) {
			error.add(fieldName+"必须为指定的枚举值");
			return false;
		}
		return true;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.join("|", error);
	}
}
