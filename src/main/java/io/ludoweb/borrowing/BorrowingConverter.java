package io.ludoweb.borrowing;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.ludoweb.game.GameConverter;

@Component
public class BorrowingConverter {

	@Autowired
	GameConverter gameConverter;

	public BorrowingView convert(BorrowingEntity entity) {
		BorrowingView view = new BorrowingView();
		view.setExternalId(entity.getExternalId());
		view.setGame(gameConverter.convert(entity.getGame()));
		view.setStartDate(entity.getStartDate());
		view.setUserExternalId(entity.getUser().getExternalId());
		return view;
	}

	public List<BorrowingView> convert(Collection<BorrowingEntity> entities) {
		return entities.stream().map(this::convert).collect(Collectors.toList());
	}
}
