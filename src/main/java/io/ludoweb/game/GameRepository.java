package io.ludoweb.game;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long>{

	Optional<GameEntity> findByExternalId(String externalId);

	void deleteByExternalId(String externalId);

}
