package io.ludoweb.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityTool {

	public static GrantedAuthority roleAuthority(String roleName) {
		return new SimpleGrantedAuthority("ROLE_" + roleName);
	}
}
