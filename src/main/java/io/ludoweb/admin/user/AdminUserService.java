package io.ludoweb.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserService implements UserDetailsService {

	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	AdminUserRepository repo;

	public boolean isAdminUser() {
		return repo.count() > 0;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByUsername(username)//
				.map(AdminUserPrincipal::new)//
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	public void save(String username, String password) {
		AdminUserEntity entity = new AdminUserEntity();
		entity.setPassword(passwordEncoder.encode(password));
		entity.setUsername(username);
		repo.save(entity);
	}
}
