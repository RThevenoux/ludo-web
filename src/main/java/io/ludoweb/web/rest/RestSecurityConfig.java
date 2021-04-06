package io.ludoweb.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.ludoweb.core.user.admin.AdminUserService;

@Configuration
@EnableWebSecurity
@Order(3)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AdminUserService service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/api/**")//
				.authorizeRequests().anyRequest()//
				.hasRole(AdminUserService.ROLE_SYNC_API)//
				.and().httpBasic()//
				.and().csrf().disable();
	}

}
