package io.ludoweb.core.borrowing;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BorrowingInput {

	@NotNull
	LocalDate endDate;

	@NotBlank
	String externalId;

	@NotBlank
	String gameExternalId;

	@NotBlank
	String memberExternalId;

	@NotNull
	LocalDate startDate;
}
