package io.ludoweb.core.borrowing;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<BorrowingEntity, Long> {

	void deleteByExternalId(String externalId);

	Optional<BorrowingEntity> findByExternalId(String externalId);

}
