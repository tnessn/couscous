package com.github.tnessn.couscous.spring.boot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author huangjinfeng
 */
@ConfigurationProperties(prefix = "couscous")
public class CouscousProperties {
    private String test;

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
  
}