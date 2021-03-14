package io.ludoweb.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordWrapper {

	@NotBlank
	String password;
}
