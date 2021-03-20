package io.ludoweb.core.adminuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUserEntity, Long> {

	Optional<AdminUserEntity> findByUsername(String username);

}
