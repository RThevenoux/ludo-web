package io.ludoweb.initialization;

import javax.validation.Valid;

import io.ludoweb.admin.user.AdminCredentialsInput;
import lombok.Data;

@Data
public class InitializationInput {

	@Valid
	AdminCredentialsInput adminCredentials;
}
