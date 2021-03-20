package io.ludoweb.core.user.member;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class PasswordWrapper {

	@NotBlank
	String password;
}
