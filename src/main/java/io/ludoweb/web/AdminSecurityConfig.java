package io.ludoweb.web;

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
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Autowired
	AdminUserService service;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/admin/**")//
				.authorizeRequests()//
				.anyRequest()//
				.hasRole(AdminUserService.ROLE_ADMIN)

				.and().formLogin()//
				.loginPage(PublicController.ADMIN_LOGIN_PAGE)//
				.loginProcessingUrl(PublicController.ADMIN_LOGIN_ACTION)//
				.failureUrl(PublicController.ADMIN_LOGIN_PAGE_LOGIN_FAIL)//
				.defaultSuccessUrl("/admin/home")

				.and().logout()//
				.logoutUrl("/admin/logout")//
				.logoutSuccessUrl("/")//
				.deleteCookies("JSESSIONID")

				.and().exceptionHandling()//
				.accessDeniedPage(PublicController.ADMIN_LOGIN_PAGE)

				.and().csrf().disable();
	}

}
