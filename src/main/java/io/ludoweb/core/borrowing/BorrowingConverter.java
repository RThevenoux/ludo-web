package io.ludoweb.core.borrowing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ludoweb.core.game.GameConverter;
import io.ludoweb.core.util.Converter;

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
		view.setMemberExternalId(entity.getMember().getExternalId());
		return view;
	}
}
