package io.ludoweb.admin.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.ludoweb.admin.AdminSecurityConfig;
import io.ludoweb.security.SecurityTool;
import lombok.Data;

@Data
public class AdminUserPrincipal implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		GrantedAuthority sgq = SecurityTool.roleAuthority(AdminSecurityConfig.ROLE_ADMIN);
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
