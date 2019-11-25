package com.github.tnessn.couscous.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.tnessn.couscous.spring.boot.starter.utils.BeanLocator;

/**
 * 
 * @author huangjinfeng
 */
@Configuration
@EnableConfigurationProperties(CouscousProperties.class)
public class CouscousConfig {
    @Autowired
    private CouscousProperties couscousProperties;
    
    
    @Bean
    public BeanLocator beanLocator() {
    	return new BeanLocator();
    }
}