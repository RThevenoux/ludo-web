package io.ludoweb.borrowing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ludoweb.game.GameConverter;
import io.ludoweb.util.Converter;

@Component
public class BorrowingConverter implements Converter<BorrowingEntity, BorrowingView> {

	@Autowired
	GameConverter gameConverter;

	@Override
	public BorrowingView apply(BorrowingEntity entity) {
		if (entity == null) {
			return null;
		}

		BorrowingView view = new BorrowingView();
		view.setExternalId(entity.getExternalId());
		view.setGame(gameConverter.apply(entity.getGame()));
		view.setStartDate(entity.getStartDate());
		view.setUserExternalId(entity.getUser().getExternalId());
		return view;
	}

}
