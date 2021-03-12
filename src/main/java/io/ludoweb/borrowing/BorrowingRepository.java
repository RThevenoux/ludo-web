package io.ludoweb.borrowing;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BorrowingRepository extends CrudRepository<BorrowingEntity, Long> {

	Optional<BorrowingEntity> findByExternalId(String externalId);

}
