package io.ludoweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.ludoweb.core.user.security.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String ROLE_USER = "USER";

	@Autowired
	MyUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/user/**")//
				.authorizeRequests()//
				.anyRequest()//
				.hasRole(ROLE_USER)

				.and().formLogin()//
				.loginPage(PublicController.USER_LOGIN_PAGE)//
				.loginProcessingUrl(PublicController.USER_LOGIN_ACTION)//
				.failureUrl(PublicController.USER_LOGIN_PAGE_LOGIN_FAIL)//
				.defaultSuccessUrl("/user/home")

				.and().logout()//
				.logoutUrl("/user/logout")//
				.logoutSuccessUrl("/")//
				.deleteCookies("JSESSIONID")

				.and().exceptionHandling()//
				.accessDeniedPage(PublicController.USER_LOGIN_PAGE)

				.and().csrf().disable();
	}

}