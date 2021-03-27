package io.ludoweb.core.user;

import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityTool {

	public static GrantedAuthority roleAuthority(String roleName) {
		return new SimpleGrantedAuthority("ROLE_" + roleName);
	}

	public static Set<GrantedAuthority> roleAuthorityAsSet(String roleName) {
		return Collections.singleton(SecurityTool.roleAuthority(roleName));
	}
}
