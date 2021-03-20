package io.ludoweb.core.game;

import org.springframework.stereotype.Component;

import io.ludoweb.core.util.Converter;

@Component
public class GameConverter implements Converter<GameEntity, GameView> {

	private static final String UNAVAILABLE = "Indisponible";
	private static final String ON_SITE = "Sur place";
	private static final String BORROWED = "Prété";
	private static final String AVAILABLE = "Disponible";

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
				return AVAILABLE;
			} else {
				return BORROWED;
			}
		} else {
			if (entity.isOnSite()) {
				return ON_SITE;
			} else {
				return UNAVAILABLE;
			}
		}
	}
}
