package com.example.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserEntity {

	@Id
	@GeneratedValue
	Long id;

	String mail;

	String password;

	String phone;

	@Column(nullable = false, unique = true)
	String username;
}
