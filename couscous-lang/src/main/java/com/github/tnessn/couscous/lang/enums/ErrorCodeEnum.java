package com.github.tnessn.couscous.lang.enums;

/**
 * 异常错误码
 * 
 * @author huangjinfeng
 */
public enum ErrorCodeEnum {

  DEFAULT(-1, "系统错误:{0}"),
  
  PARAM_ERROR(1000, "参数绑定异常【{0}】"), 
  AUTH_ERROR(1001, "验证失败"),
  PASS_ERROR(1002,"密码输入错误{0}次,{1}次错误将限制登录"), 
  NO_PERMISSON(1004,"无权限,不能访问"), 
  CAPTCHA_ERROR(1005, "验证码错误"), 
  USER_HAVE_REGISTERED(1006, "用户已经注册"),
  EXCESSIVE_ATTEMPTS(1007,"密码输错{0}次,帐号{1}分钟后可登录"), 
  UNKNOWN_ACCOUNT(1008,"用户不存在"),
  LOCKED_ACCOUNT(1009, "帐户已被锁定"), 
  OLD_PWD_ERROR(1010,"旧密码错误"),
  ROLE_EXIST(1011,"角色已存在"),
 ;



  public static ErrorCodeEnum getEnumByTxCode(Integer code) {
    ErrorCodeEnum[] allEnums = values();
    for (ErrorCodeEnum item : allEnums) {
      if (item.getCode().equals(code))
        return item;
    }
    return null;
  }

  private Integer code;

  private String desc;

  ErrorCodeEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

public Integer getCode() {
	return code;
}

public void setCode(Integer code) {
	this.code = code;
}

public String getDesc() {
	return desc;
}

public void setDesc(String desc) {
	this.desc = desc;
}
}
