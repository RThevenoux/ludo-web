package io.ludoweb.game;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	@Autowired
	GameRepository repo;

	public Optional<GameEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}
}
