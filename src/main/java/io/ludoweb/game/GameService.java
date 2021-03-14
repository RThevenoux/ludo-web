package io.ludoweb.game;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameService {

	@Autowired
	GameConverter converter;
	@Autowired
	GameRepository repo;

	public GameView createOrUpdate(GameInput input) {
		GameEntity entity = createOrUpdateEntity(input);
		return converter.apply(entity);
	}

	private GameEntity createOrUpdateEntity(GameInput input) {
		Optional<GameEntity> opt = repo.findByExternalId(input.getExternalId());

		if (opt.isPresent()) {
			GameEntity entity = opt.get();
			fill(entity, input);
			return entity;
		} else {
			GameEntity newEntity = new GameEntity();
			newEntity.setExternalId(input.getExternalId());

			fill(newEntity, input);

			return repo.save(newEntity);
		}
	}

	public void deleteByExternalId(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	private void fill(GameEntity entity, GameInput input) {
		entity.setName(input.getName());
	}

	public Optional<GameView> findByExternalId(String externalId) {
		return findEntityByExternalId(externalId).map(converter);
	}

	public Optional<GameEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public List<GameView> listAll() {
		return converter.convert(repo.findAll());
	}
}
