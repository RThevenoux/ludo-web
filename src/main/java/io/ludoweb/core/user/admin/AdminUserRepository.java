package io.ludoweb.core.user.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

	long countByRole(String role);

	List<AdminUserEntity> findByRole(String role);

	Optional<AdminUserEntity> findByUsername(String username);
}
