package io.ludoweb.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.ludoweb.borrowing.BorrowingEntity;
import io.ludoweb.user.plan.Plan;
import lombok.Data;

@Entity
@Data
public class UserEntity {

	@OneToMany(mappedBy = "user")
	List<BorrowingEntity> borrowings;

	String externalId;

	String firstName;

	@Id
	@GeneratedValue
	Long id;

	String lastName;

	String mail;

	@Convert(converter = ListConverter.class)
	List<String> otherMembers;

	String password;

	String phone;

	@Embedded
	Plan plan;

	String type;

	@Column(nullable = false, unique = true)
	String username;
}
