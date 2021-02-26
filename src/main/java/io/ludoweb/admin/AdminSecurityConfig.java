package io.ludoweb.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.ludoweb.PublicController;

@Configuration
@EnableWebSecurity
@Order(1)
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ROLE_ADMIN = "ADMIN";

	@Autowired
	PasswordEncoder encoder;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/admin/**")//
				.authorizeRequests()//
				.anyRequest()//
				.hasRole(ROLE_ADMIN)

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

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()//
				.withUser("admin").password(encoder.encode("password")).roles(ROLE_ADMIN);

	}

}
