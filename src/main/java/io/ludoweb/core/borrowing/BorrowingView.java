package io.ludoweb.core.borrowing;

import java.time.LocalDate;

import io.ludoweb.core.game.GameView;
import lombok.Data;

@Data
public class BorrowingView {

	LocalDate endDate;

	String externalId;

	GameView game;

	String memberExternalId;
	
	LocalDate startDate;

}
