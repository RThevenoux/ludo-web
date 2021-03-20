package io.ludoweb.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminUserService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AdminUserRepository repo;

	@Transactional
	public void createOrUpdateAdminUser(AdminCredentialsInput input) {
		List<AdminUserEntity> entities = repo.findAll();

		// Create a new one or update existing
		if (entities.isEmpty()) {
			AdminUserEntity entity = new AdminUserEntity();
			fill(entity, input);
			repo.save(entity);
		} else {
			AdminUserEntity entity = entities.get(0);
			fill(entity, input);
		}
	}

	private void fill(AdminUserEntity entity, AdminCredentialsInput input) {
		entity.setPassword(passwordEncoder.encode(input.getPassword()));
		entity.setUsername(input.getUsername());
	}

	public boolean isAdminUser() {
		return repo.count() > 0;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username)//
				.map(AdminUserPrincipal::new)//
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}
}
