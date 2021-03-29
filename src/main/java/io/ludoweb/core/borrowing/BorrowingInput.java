package io.ludoweb.core.borrowing;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BorrowingInput {

	@NotBlank
	String externalId;

	@NotBlank
	String gameExternalId;

	@NotNull
	LocalDate startDate;

	@NotBlank
	String memberExternalId;
}
