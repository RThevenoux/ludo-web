package io.ludoweb.core.initialization;

import javax.validation.Valid;

import io.ludoweb.core.user.admin.CredentialsInput;
import lombok.Data;

@Data
public class InitializationInput {

	@Valid
	CredentialsInput adminCredentials;
}
