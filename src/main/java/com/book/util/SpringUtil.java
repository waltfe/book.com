package com.book.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static HttpServletRequest request;
	
    private static HttpSession session;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
        
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    
    //获取class的实现类
    public static <T> Map<String, T> getBeans(Class<T> clazz){
    	return getApplicationContext().getBeansOfType(clazz);
    }

	public static HttpServletRequest getRequest() {
		return request;
	}
	
	@Autowired
	public void setRequest(HttpServletRequest request) {
		SpringUtil.request = request;
	}

	public static HttpSession getSession() {
		return session;
	}
	
	@Autowired
	public void setSession(HttpSession session) {
		SpringUtil.session = session;
	}

}
