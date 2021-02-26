package io.ludoweb.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	UserEntity findByUsername(String username);

	boolean existsByUsername(String username);

	void deleteByUsername(String username);
}
