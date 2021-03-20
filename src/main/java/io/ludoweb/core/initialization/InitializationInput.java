package io.ludoweb.core.initialization;

import javax.validation.Valid;

import io.ludoweb.core.user.admin.AdminCredentialsInput;
import lombok.Data;

@Data
public class InitializationInput {

	@Valid
	AdminCredentialsInput adminCredentials;
}
