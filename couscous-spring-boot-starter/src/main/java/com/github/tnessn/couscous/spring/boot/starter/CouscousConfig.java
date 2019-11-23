package com.github.tnessn.couscous.spring.boot.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author huangjinfeng
 */
@Configuration
@EnableConfigurationProperties(CouscousProperties.class)
public class CouscousConfig {
    @Autowired
    private CouscousProperties couscousProperties;

}