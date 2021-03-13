package io.ludoweb.game;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameService {

	@Autowired
	GameRepository repo;

	private GameView convert(GameEntity entity) {
		System.out.println("Entity: "+entity);
		GameView view = new GameView();
		view.setExternalId(entity.getExternalId());
		view.setName(entity.getName());
		return view;
	}

	public Optional<GameEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public List<GameView> listAll() {
		return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
	}

	public Optional<GameView> findByExternalId(String externalId) {
		return findEntityByExternalId(externalId).map(this::convert);
	}

	public void deleteByExternalId(String externalId) {
		repo.deleteByExternalId(externalId);
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

	public GameView createOrUpdate(GameInput input) {
		GameEntity entity = createOrUpdateEntity(input);
		return convert(entity);
	}

	private void fill(GameEntity entity, GameInput input) {
		entity.setName(input.getName());
	}
}
