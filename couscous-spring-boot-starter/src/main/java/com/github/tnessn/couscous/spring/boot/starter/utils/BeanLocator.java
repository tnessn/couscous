package com.github.tnessn.couscous.spring.boot.starter.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


// TODO: Auto-generated Javadoc
/**
 * 使用时，需注入到spring容器中.
 *
 * @author huangjinfeng
 */
public class BeanLocator implements ApplicationContextAware {

	/** Spring应用上下文环境. */
    private static ApplicationContext ctx;    

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境.
     *
     * @param ctx the new application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        BeanLocator.ctx = ctx;
    }

    /**
     * 获取对象.
     *
     * @param <T> the generic type
     * @param name the name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException the beans exception
     */
    @SuppressWarnings("unchecked")
    public static  <T> T  getBean(String name) throws BeansException {
        assertContextInjected();
        return  (T)ctx.getBean(name);
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        if (ctx == null) {
            throw new IllegalStateException("Spring application context未注入,请在spring配置文件中定义BeanLocator");
        }
    }
}
