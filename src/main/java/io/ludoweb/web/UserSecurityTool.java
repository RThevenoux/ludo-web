package io.ludoweb.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.ludoweb.core.user.security.MyUserPrincipal;

public class UserSecurityTool {

	static public MyUserPrincipal getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (MyUserPrincipal) auth.getPrincipal();
	}
}
