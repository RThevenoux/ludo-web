package io.ludoweb.core.user.member.email;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

@Service
public class EmailValidatorService {

	EmailValidator validator = EmailValidator.getInstance();

	public boolean isValid(String email) {
		return validate(email) == EmailValidation.VALID;
	}

	public EmailValidation validate(String email) {
		if (email == null || email.isEmpty()) {
			return EmailValidation.EMPTY;
		}

		return (validator.isValid(email) ? EmailValidation.VALID : EmailValidation.INVALID);
	}
}
