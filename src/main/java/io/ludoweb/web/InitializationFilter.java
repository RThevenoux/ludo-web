package io.ludoweb.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import io.ludoweb.core.initialization.InitializationService;

@Component
@Order(0)
public class InitializationFilter extends OncePerRequestFilter {

	AntPathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	InitializationService service;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (service.isInitialized()) {
			filterChain.doFilter(request, response);
		} else {
			response.sendRedirect(InitializationController.INIT_PATH);
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return pathMatcher.match(InitializationController.INIT_PATH, request.getServletPath());
	}

}
