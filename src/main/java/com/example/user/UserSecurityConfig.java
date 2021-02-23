package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder encoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/user/**")
        .authorizeRequests()
        .anyRequest()
        .hasRole("USER")
        
        .and()
        .formLogin()
        .loginPage("/loginUser.html")
        .loginProcessingUrl("/user/login")
        .failureUrl("/loginUser.html?error=loginError")
        .defaultSuccessUrl("/user/test")
        
        .and()
        .logout()
        .logoutUrl("/user/logout")
        .logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID")
        
        .and()
        .exceptionHandling()
        .accessDeniedPage("/403")
        
        .and()
        .csrf().disable();
	}
	
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	          .withUser("user")
	          .password(encoder.encode("password"))
	          .roles("USER");
	    }

}
