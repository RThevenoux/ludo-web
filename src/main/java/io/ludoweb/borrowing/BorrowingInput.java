package io.ludoweb.borrowing;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BorrowingInput {

	@NotBlank
	String externalId;

	@NotBlank
	String gameExternalId;

	@NotBlank
	String userExternalId;

	@NotBlank
	String startDate;
}
