package io.ludoweb.borrowing;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BorrowingInput {

	@NotBlank
	String externalId;

	@NotBlank
	String gameExternalId;

	@NotBlank
	String userExternalId;

	@NotEmpty
	String startDate;

	String endDate;
}
