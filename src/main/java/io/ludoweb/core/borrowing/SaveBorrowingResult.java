package io.ludoweb.core.borrowing;

import lombok.Value;

@Value
public class SaveBorrowingResult {

	public static SaveBorrowingResult fail(boolean memberFound, boolean gameFound) {
		return new SaveBorrowingResult(gameFound, memberFound, false);
	}

	public static SaveBorrowingResult success() {
		return new SaveBorrowingResult(true, true, true);
	}

	boolean gameFound;
	boolean memberFound;
	boolean success;
}
