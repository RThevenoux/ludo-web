package io.ludoweb.borrowing;

import io.ludoweb.game.GameView;
import lombok.Data;

@Data
public class BorrowingView {

	String externalId;

	GameView game;

	String startDate;

	String userExternalId;

}
