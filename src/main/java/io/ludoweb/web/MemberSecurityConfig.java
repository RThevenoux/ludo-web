package io.ludoweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.ludoweb.core.user.member.MemberPrincipal;
import io.ludoweb.core.user.member.MemberService;

@Configuration
@EnableWebSecurity
@Order(2)
public class MemberSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MemberService memberService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/member/**")//
				.authorizeRequests()//
				.anyRequest()//
				.hasRole(MemberPrincipal.ROLE_MEMBER)

				.and().formLogin()//
				.loginPage(PublicController.USER_LOGIN_PAGE)//
				.loginProcessingUrl(PublicController.USER_LOGIN_ACTION)//
				.failureUrl(PublicController.USER_LOGIN_PAGE_LOGIN_FAIL)//
				.defaultSuccessUrl("/member/home")

				.and().logout()//
				.logoutUrl("/member/logout")//
				.logoutSuccessUrl("/")//
				.deleteCookies("JSESSIONID")

				.and().exceptionHandling()//
				.accessDeniedPage(PublicController.USER_LOGIN_PAGE)

				.and().csrf().disable();
	}

}
