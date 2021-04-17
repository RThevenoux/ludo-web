package io.ludoweb.core.user.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.ludoweb.core.user.MyUserDetails;
import io.ludoweb.core.user.SecurityTool;

@Service
public class AdminUserService implements UserDetailsService {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_SYNC_API = "SYNC_API";

	@Autowired
	AdminUserConverter converter;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AdminUserRepository repo;

	private UserDetails buildDetails(AdminUserEntity entity) {
		MyUserDetails details = new MyUserDetails();
		details.setPassword(entity.getPassword());
		details.setUsername(entity.getUsername());
		details.setAuthorities(SecurityTool.roleAuthorityAsSet(entity.getRole()));
		return details;
	}

	/**
	 * If a user exists with same username and role : <strong>update</strong>
	 * password<br>
	 * 
	 * If a user exists with same role and different username :
	 * <strong>update</strong> username and password<br>
	 * 
	 * If a user exists with same username and different role :
	 * <strong>fail</strong><br>
	 * 
	 * If no user exist with same username nor role : <strong>create</strong> a new
	 * user
	 * 
	 * @param input
	 * @param role
	 * @return
	 */
	private boolean createOrUpdate(CredentialsInput input, String role) {
		Optional<AdminUserEntity> opt = repo.findByUsername(input.getUsername());

		if (opt.isPresent()) {// IF entity exist with same username
			AdminUserEntity entity = opt.get();
			if (entity.getRole().equals(role)) {
				// Update existing (only password)
				fill(entity, input, role);
				return true;
			} else {
				// ERROR
				return false;
			}
		} else { // NO entity exists with same username
			List<AdminUserEntity> entities = repo.findByRole(role);
			if (entities.isEmpty()) {
				// Create a new one
				AdminUserEntity entity = new AdminUserEntity();
				fill(entity, input, role);
				repo.save(entity);
				return true;
			} else {
				// Update existing (password and username)
				AdminUserEntity entity = entities.get(0);
				fill(entity, input, role);
				return true;
			}
		}
	}

	@Transactional
	public boolean createOrUpdateAdminUser(CredentialsInput input) {
		return createOrUpdate(input, ROLE_ADMIN);
	}

	@Transactional
	public boolean createOrUpdateApiSyncUser(CredentialsInput input) {
		return createOrUpdate(input, ROLE_SYNC_API);
	}

	private void fill(AdminUserEntity entity, CredentialsInput input, String role) {
		entity.setPassword(passwordEncoder.encode(input.getPassword()));
		entity.setUsername(input.getUsername());
		entity.setRole(role);
	}

	public AdminUserListing getListing() {
		Optional<AdminUserEntity> admin = repo.findByRole(ROLE_ADMIN).stream().findFirst();
		Optional<AdminUserEntity> apiSync = repo.findByRole(ROLE_SYNC_API).stream().findFirst();

		AdminUserListing info = new AdminUserListing();
		admin.map(converter).ifPresent(info::setAdmin);
		apiSync.map(converter).ifPresent(info::setApiSync);

		return info;
	}

	public boolean isAdminUser() {
		return repo.countByRole(ROLE_ADMIN) > 0;
	}

	public boolean isApiSyncUser() {
		return repo.countByRole(ROLE_SYNC_API) > 0;
	}

	// Implement UserDetailsService (Spring-Security)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username)//
				.map(this::buildDetails)//
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
