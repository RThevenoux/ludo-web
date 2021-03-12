package io.ludoweb.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);

	@Query("SELECT u FROM UserEntity u WHERE u.plan.subscriptionPaid = :subscriptionPaid")
	List<UserEntity> findBySubscriptionPaid(boolean subscriptionPaid);

	boolean existsByExternalId(String externalId);

	void deleteByExternalId(String externalId);

	Optional<UserEntity> findByExternalId(String externalId);
}
