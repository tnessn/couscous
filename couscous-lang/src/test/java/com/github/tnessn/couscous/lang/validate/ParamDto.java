package com.github.tnessn.couscous.lang.validate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

// TODO: Auto-generated Javadoc
/**
 * The Class ParamDto.
 *
 * @author huangjinfeng
 * @date:2018-05-25 
 */
public class ParamDto {
	

	/** 支付完成时间. */
	//@NotEmpty(message="支付完成时间不能空",groups=Group2.class)
	@Size(min=8,max=14,message="支付完成时间长度{min}-{max}位")
	private String payTime;
	
	/** The name. */
	@NotEmpty(message="姓名不能为空",groups=Group1.class)
	private String name;
	

	/**
	 * Gets the pay time.
	 *
	 * @return the pay time
	 */
	public String getPayTime() {
	return payTime;
	}

	/**
	 * Sets the pay time.
	 *
	 * @param payTime the new pay time
	 */
	public void setPayTime(String payTime) {
	this.payTime = payTime;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
