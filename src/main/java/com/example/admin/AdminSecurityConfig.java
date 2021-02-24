package com.example.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.PublicController;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder encoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.antMatcher("/admin/**")
         .authorizeRequests()
         .anyRequest()
         .hasRole("ADMIN")
         
         .and()
         .formLogin()
         .loginPage(PublicController.ADMIN_LOGIN_PAGE)
         .loginProcessingUrl("/admin/login")
         .failureUrl(PublicController.ADMIN_LOGIN_PAGE+"?error=loginError")
         .defaultSuccessUrl("/admin/test")
         
         .and()
         .logout()
         .logoutUrl("/admin/logout")
         .logoutSuccessUrl("/")
         .deleteCookies("JSESSIONID")
         
         .and()
         .exceptionHandling()
         .accessDeniedPage(PublicController.ADMIN_LOGIN_PAGE+"?accessDenied=true")
         
         .and()
         .csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()//
				.withUser("admin")
				.password(encoder.encode("password"))
				.roles("ADMIN");
		
	}


}
