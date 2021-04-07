package io.ludoweb.core.user.member.email;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ludoweb.core.user.member.MemberConverter;
import io.ludoweb.core.user.member.MemberEntity;
import io.ludoweb.core.user.member.MemberView;

@Service
public class EmailResultFactory {

	@Autowired
	EmailValidatorService validator;
	@Autowired
	MemberConverter converter;

	public EmailResult build(Iterable<MemberEntity> members) {

		List<String> emails = new ArrayList<>();
		List<MemberView> invalidEmailMembers = new ArrayList<>();
		List<MemberView> noEmailMembers = new ArrayList<>();

		for (MemberEntity member : members) {
			String email = member.getEmail();

			EmailValidation validation = validator.validate(email);
			switch (validation) {
			case VALID:
				emails.add(email);
				break;
			case INVALID:
				invalidEmailMembers.add(converter.apply(member));
				break;
			case EMPTY:
				noEmailMembers.add(converter.apply(member));
				break;
			default:
				break;
			}
		}

		EmailResult result = new EmailResult();
		result.setEmails(emails);
		result.setInvalidEmailMembers(invalidEmailMembers);
		result.setNoEmailMembers(noEmailMembers);

		return result;
	}
}
