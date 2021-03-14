package io.ludoweb.game;

import org.springframework.stereotype.Component;

import io.ludoweb.util.Converter;

@Component
public class GameConverter implements Converter<GameEntity, GameView> {

	@Override
	public GameView apply(GameEntity entity) {
		if (entity == null) {
			return null;
		}

		GameView view = new GameView();
		view.setExternalId(entity.getExternalId());
		view.setName(entity.getName());
		return view;
	}
}
