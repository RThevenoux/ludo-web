package io.ludoweb.core.user.admin;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CredentialsInput {

	@NotBlank
	String password;

	@NotBlank
	String username;
}
