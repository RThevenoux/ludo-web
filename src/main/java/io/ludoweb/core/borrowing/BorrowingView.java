package io.ludoweb.core.borrowing;

import java.time.LocalDate;

import io.ludoweb.core.game.GameView;
import lombok.Data;

@Data
public class BorrowingView {

	String externalId;

	GameView game;

	LocalDate startDate;

	String memberExternalId;

}
