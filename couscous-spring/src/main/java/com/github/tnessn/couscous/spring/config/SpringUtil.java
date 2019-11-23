package com.github.tnessn.couscous.spring.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


// TODO: Auto-generated Javadoc
/**
 * 通过spring文件初始化.
 *
 * @author huangjinfeng
 */
public class SpringUtil {

    /** The context. */
    private static ApplicationContext context;

    /**
     * Inits the by class path.
     *
     * @param springFileInClassPath the spring file in class path
     */
    public static void initByClassPath(String springFileInClassPath){
        context = new ClassPathXmlApplicationContext("classpath:"+springFileInClassPath);
        if(context instanceof ConfigurableApplicationContext){
            ((ConfigurableApplicationContext)context).registerShutdownHook();
        }
    }
    
    
    
    /**
     * Inits the by file path array.
     *
     * @param absolutePathArray the absolute path array
     */
    public static void initByFilePathArray(String... absolutePathArray){
        context = new FileSystemXmlApplicationContext(absolutePathArray);
        if(context instanceof ConfigurableApplicationContext){
            ((ConfigurableApplicationContext)context).registerShutdownHook();
        }
    }

}
