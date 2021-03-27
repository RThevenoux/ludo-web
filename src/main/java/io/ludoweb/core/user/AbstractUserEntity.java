package io.ludoweb.core.user;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class AbstractUserEntity {

	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false, unique = true)
	String password;

	@Column(nullable = false)
	String username;

	@Column(nullable = false)
	String role;
}
