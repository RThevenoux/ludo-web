package com.example.user;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class Borrowing {

	@NotEmpty
	String itemName;

	@NotEmpty
	String startDate;
}
