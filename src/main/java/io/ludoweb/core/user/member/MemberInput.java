package io.ludoweb.core.user.member;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.ludoweb.core.user.member.plan.Plan;
import lombok.Data;

@Data
public class MemberInput {

	@NotBlank
	String externalId;

	String email;

	String firstName;

	String lastName;

	List<@NotEmpty String> otherMembers;

	String phone;

	@Valid
	Plan plan;

	String type;

	@NotBlank
	String username;
}
