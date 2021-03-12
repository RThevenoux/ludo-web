package io.ludoweb.game;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<GameEntity, Long>{

	Optional<GameEntity> findByExternalId(String externalId);

}
