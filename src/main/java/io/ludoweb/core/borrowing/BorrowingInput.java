package io.ludoweb.core.borrowing;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BorrowingInput {

	@NotBlank
	String externalId;

	@NotBlank
	String gameExternalId;

	@NotBlank
	String startDate;

	@NotBlank
	String userExternalId;
}
