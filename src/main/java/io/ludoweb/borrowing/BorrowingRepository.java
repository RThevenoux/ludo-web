package io.ludoweb.borrowing;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<BorrowingEntity, Long> {

	Optional<BorrowingEntity> findByExternalId(String externalId);

	void deleteByExternalId(String externalId);

}
