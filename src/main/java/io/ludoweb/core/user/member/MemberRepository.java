package io.ludoweb.core.user.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	void deleteByExternalId(String externalId);

	boolean existsByExternalId(String externalId);

	Optional<MemberEntity> findByExternalId(String externalId);

	@Query("SELECT u FROM MemberEntity u WHERE u.plan.subscriptionPaid = :subscriptionPaid")
	List<MemberEntity> findBySubscriptionPaid(boolean subscriptionPaid);

	Optional<MemberEntity> findByUsername(String username);
}
