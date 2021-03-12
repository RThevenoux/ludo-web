package io.ludoweb.user.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserSecurityTool {

	static public MyUserPrincipal getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (MyUserPrincipal) auth.getPrincipal();
	}
}
