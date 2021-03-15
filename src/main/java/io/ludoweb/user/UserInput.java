package io.ludoweb.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.ludoweb.user.plan.Plan;
import lombok.Data;

@Data
public class UserInput {

	@NotBlank
	String externalId;

	String firstName;

	String lastName;

	String mail;

	List<@NotEmpty String> otherMembers;

	String phone;

	@Valid
	Plan plan;

	String type;

	@NotBlank
	String username;
}
