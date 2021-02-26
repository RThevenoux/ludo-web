package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.PublicController;

@Configuration
@EnableWebSecurity
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MyUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/user/**")//
				.authorizeRequests()//
				.anyRequest()//
				.hasRole("USER")

				.and().formLogin()//
				.loginPage(PublicController.USER_LOGIN_PAGE)//
				.loginProcessingUrl("/user/login")//
				.failureUrl(PublicController.USER_LOGIN_PAGE + "?error=loginError")//
				.defaultSuccessUrl("/user/home")

				.and().logout()//
				.logoutUrl("/user/logout")//
				.logoutSuccessUrl("/")//
				.deleteCookies("JSESSIONID")

				.and().exceptionHandling()//
				.accessDeniedPage(PublicController.USER_LOGIN_PAGE + "?accessDenied=true")

				.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
