package demo1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * A simple implementation of a <tt>BeanPostProcessor</tt> that logs every bean
 * created by Spring. When working with Spring Boot, a lot of beans get created
 * "auto-magically". This logger allows you to see what beans have been setup.
 * <p>
 * You should remember that most of the web-layer beans are created by Spring
 * MVC automatically, whether you are using Spring Boot or not. To see what
 * beans are created, examine the <tt>@Bean</tt> methods in
 * {@link WebMvcConfigurationSupport}.
 * 
 * @author Paul Chapman
 */
@Component
public class BeanLogger implements BeanPostProcessor {

	protected Logger logger;

	public BeanLogger() {
		logger = LoggerFactory.getLogger(getClass());
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		// Nothing to do. Must return the bean or we lose it!
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		// Display the bean. If it is Ordered, print its order also. Several MVC
		// classes are chained by Order and it is often useful to see what order
		// they are configured to run.
		if (bean instanceof Ordered) {
			String order = (bean instanceof Ordered) ? String
					.valueOf(((Ordered) bean).getOrder()) : "unknown";
			logger.info(bean.getClass().getName() + " - Order: " + order);
		} else {
			logger.info(bean.getClass().getName());
		}

		// Must return the bean or we lose it!
		return bean;
	}

}
