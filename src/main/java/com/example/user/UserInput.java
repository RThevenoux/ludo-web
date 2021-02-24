package com.example.user;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserInput {

	// can be null
	String mail;

	// can be null
	String phone;

	@NotEmpty
	String username;
}
