package io.ludoweb.core.user;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordWrapper {

	@NotBlank
	String password;
}
