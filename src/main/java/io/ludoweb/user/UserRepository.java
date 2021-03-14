package io.ludoweb.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	void deleteByExternalId(String externalId);

	boolean existsByExternalId(String externalId);

	Optional<UserEntity> findByExternalId(String externalId);

	@Query("SELECT u FROM UserEntity u WHERE u.plan.subscriptionPaid = :subscriptionPaid")
	List<UserEntity> findBySubscriptionPaid(boolean subscriptionPaid);

	Optional<UserEntity> findByUsername(String username);
}
