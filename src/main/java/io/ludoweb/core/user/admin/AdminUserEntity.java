package io.ludoweb.core.user.admin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AdminUserEntity {

	@Id
	@GeneratedValue
	Long id;

	String password;

	String username;
}
