package com.example.user;

import java.util.List;

import lombok.Data;

@Data
public class UserView {

	String firstName;

	String lastName;

	String mail;

	String phone;

	boolean password;

	String plan;

	Boolean subscriptionPaid;

	String type;

	String username;

	List<String> otherMembers;

	List<Borrowing> borrowings;
}
