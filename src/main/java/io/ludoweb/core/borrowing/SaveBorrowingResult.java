package io.ludoweb.core.borrowing;

import lombok.Value;

@Value
public class SaveBorrowingResult {

	public static SaveBorrowingResult fail(boolean userFound, boolean gameFound) {
		return new SaveBorrowingResult(false, userFound, gameFound);
	}

	public static SaveBorrowingResult success() {
		return new SaveBorrowingResult(true, true, true);
	}

	boolean gameFound;
	boolean success;
	boolean userFound;
}
