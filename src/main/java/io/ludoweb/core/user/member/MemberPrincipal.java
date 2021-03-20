package io.ludoweb.core.user.member;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.ludoweb.core.util.SecurityTool;
import lombok.Getter;

/**
 * Principal expect by Spring-Security
 *
 */
@Getter
public class MemberPrincipal implements UserDetails {

	public static final String ROLE_MEMBER = "MEMBER";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	long id;
	String password;
	String username;

	public MemberPrincipal(MemberEntity user) {
		this.password = user.getPassword();
		this.username = user.getUsername();
		this.id = user.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority sgq = SecurityTool.roleAuthority(ROLE_MEMBER);
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
