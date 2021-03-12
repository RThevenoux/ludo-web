package io.ludoweb.user.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.ludoweb.security.SecurityTool;
import io.ludoweb.user.UserEntity;
import lombok.Getter;

@Getter
public class MyUserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String password;
	String username;
	long id;

	public MyUserPrincipal(UserEntity user) {
		this.password = user.getPassword();
		this.username = user.getUsername();
		this.id = user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority sgq = SecurityTool.roleAuthority(UserSecurityConfig.ROLE_USER);
		return Collections.singleton(sgq);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
