package io.ludoweb.initialization;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializationConfiguration {

	@Bean
	InitializationFilter initailizationFilter() {
		return new InitializationFilter();
	}

	@Bean
	public FilterRegistrationBean<InitializationFilter> someFilterRegistration() {
		FilterRegistrationBean<InitializationFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(initailizationFilter());

		registration.addUrlPatterns("/");
		registration.addUrlPatterns("/game");
		registration.addUrlPatterns("/user/*");
		registration.addUrlPatterns("/admin/*");
		registration.addUrlPatterns("/public/*");

		registration.setName("initailizationFilter");
		registration.setOrder(1);
		return registration;
	}
}
