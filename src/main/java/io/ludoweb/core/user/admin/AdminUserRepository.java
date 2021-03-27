package io.ludoweb.core.user.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

	Optional<AdminUserEntity> findByUsername(String username);
	
	List<AdminUserEntity> findByRole(String role);

	long countByRole(String role);
}
