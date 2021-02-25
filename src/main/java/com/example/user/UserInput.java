package com.example.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UserInput {

	String firstName;

	String lastName;

	String mail;

	String phone;

	String plan;

	@NotNull
	Boolean subscriptionPaid;

	String type;

	@NotEmpty
	String username;

	List<@NotEmpty String> otherMembers;

	List<@Valid Borrowing> borrowings;
}
