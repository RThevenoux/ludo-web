package io.ludoweb.core.initialization;

import javax.validation.Valid;

import io.ludoweb.core.adminuser.AdminCredentialsInput;
import lombok.Data;

@Data
public class InitializationInput {

	@Valid
	AdminCredentialsInput adminCredentials;
}
