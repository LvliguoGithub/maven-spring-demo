/**
 * 
 */
package net.demo.llg.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * history:
 * 2016年6月28日 mis_zym
 */
@Component
public class SpringUtil implements ApplicationContextAware{
	private static ApplicationContext context;
	
	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return context.getBean(beanName, beanClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
}
