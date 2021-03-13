package io.ludoweb.game;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class GameConverter {

	public GameView convert(GameEntity entity) {
		GameView view = new GameView();
		view.setExternalId(entity.getExternalId());
		view.setName(entity.getName());
		return view;
	}

	public List<GameView> convert(Collection<GameEntity> entities) {
		return entities.stream().map(this::convert).collect(Collectors.toList());
	}

}
