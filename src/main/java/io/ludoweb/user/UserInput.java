package io.ludoweb.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

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

	@NotNull
	Boolean subscriptionPaid;

	String type;

	@NotBlank
	String username;
}
