package io.ludoweb.user.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import io.ludoweb.user.UserEntity;

public class UserSecurityTool {

	static public UserEntity getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserPrincipal principal = (MyUserPrincipal) auth.getPrincipal();
		return principal.getUser();
	}
}
