package io.ludoweb.core.user.admin;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.ludoweb.core.util.SecurityTool;
import lombok.Data;

/**
 * Principal expect by Spring-Security
 *
 */
@Data
public class AdminUserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ROLE_ADMIN = "ADMIN";

	long id;
	String password;
	String username;

	public AdminUserPrincipal(AdminUserEntity user) {
		this.password = user.getPassword();
		this.username = user.getUsername();
		this.id = user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority sgq = SecurityTool.roleAuthority(ROLE_ADMIN);
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
