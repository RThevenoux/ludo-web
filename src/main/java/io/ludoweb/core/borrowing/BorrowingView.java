package io.ludoweb.core.borrowing;

import io.ludoweb.core.game.GameView;
import lombok.Data;

@Data
public class BorrowingView {

	String externalId;

	GameView game;

	String startDate;

	String userExternalId;

}
