package com.example.user;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PasswordWrapper {

	@NotEmpty
	String password;
}
