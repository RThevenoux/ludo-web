package com.example.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserEntity {

	String firstName;

	@Id
	@GeneratedValue
	Long id;

	String lastName;

	String mail;

	String password;

	String phone;

	String plan;

	boolean subscriptionPaid;

	String type;

	@Column(nullable = false, unique = true)
	String username;

	@Convert(converter = ListConverter.class)
	List<String> otherMembers;

	@Convert(converter = BorrowingsConverter.class)
	List<Borrowing> borrowings;
}
