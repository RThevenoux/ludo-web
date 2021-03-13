package io.ludoweb.borrowing;

import lombok.Value;

@Value
public class BorrowingResult {

	public static BorrowingResult fail(boolean userFound, boolean gameFound) {
		return new BorrowingResult(false, userFound, gameFound);
	}

	public static BorrowingResult success() {
		return new BorrowingResult(true, true, true);
	}

	boolean success;
	boolean userFound;
	boolean gameFound;
}
