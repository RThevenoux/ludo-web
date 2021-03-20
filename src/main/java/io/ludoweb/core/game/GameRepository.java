package io.ludoweb.core.game;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

	void deleteByExternalId(String externalId);

	Optional<GameEntity> findByExternalId(String externalId);

}
