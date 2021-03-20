package io.ludoweb.core.game;

import org.springframework.stereotype.Component;

import io.ludoweb.core.util.Converter;

@Component
public class GameConverter implements Converter<GameEntity, GameView> {

	@Override
	public GameView apply(GameEntity entity) {
		if (entity == null) {
			return null;
		}

		GameView view = new GameView();
		view.setAgeCategory(entity.getAgeCategory());
		view.setBorrowable(entity.isBorrowable());
		view.setDurationCategory(entity.getDurationCategory());
		view.setExternalId(entity.getExternalId());
		view.setName(entity.getName());
		view.setOnSite(entity.isOnSite());
		view.setPlayerCount(entity.getPlayerCount());
		view.setStatus(computeStatus(entity));
		view.setType(entity.getType());

		return view;
	}

	private String computeStatus(GameEntity entity) {
		if (entity.isBorrowable()) {
			if (entity.getBorrowings() == null || entity.getBorrowings().isEmpty()) {
				return "Disponible";
			} else {
				return "Prété";
			}
		} else {
			if (entity.isOnSite()) {
				return "Sur place";
			} else {
				return "Indisponible";
			}
		}
	}
}
