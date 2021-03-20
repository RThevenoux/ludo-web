package io.ludoweb.core.user.member;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.ludoweb.core.borrowing.BorrowingEntity;
import io.ludoweb.core.user.member.plan.Plan;
import io.ludoweb.core.util.ListConverter;
import lombok.Data;

@Entity
@Data
public class MemberEntity {

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	List<BorrowingEntity> borrowings;

	@Column(nullable = false, unique = true)
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
