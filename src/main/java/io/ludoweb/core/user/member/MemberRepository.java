package io.ludoweb.core.user.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>, QuerydslPredicateExecutor<MemberEntity> {

	void deleteByExternalId(String externalId);

	boolean existsByExternalId(String externalId);

	Optional<MemberEntity> findByExternalId(String externalId);

	@Query("SELECT m FROM MemberEntity m WHERE m.plan.subscriptionPaid = :subscriptionPaid")
	List<MemberEntity> findBySubscriptionPaid(boolean subscriptionPaid);

	Optional<MemberEntity> findByUsername(String username);

	@Query("SELECT DISTINCT m.plan.name FROM MemberEntity m")
	List<String> listPlanNames();

	@Query("SELECT DISTINCT m.type FROM MemberEntity m")
	List<String> listTypes();

}
