package io.ludoweb.core.user.member;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import io.ludoweb.core.borrowing.BorrowingEntity;
import io.ludoweb.core.user.AbstractUserEntity;
import io.ludoweb.core.user.member.plan.Plan;
import io.ludoweb.core.util.ListConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AttributeOverrides({ @AttributeOverride(name = "password", column = @Column(nullable = true)) })
public class MemberEntity extends AbstractUserEntity {

	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	List<BorrowingEntity> borrowings;

	String email;

	boolean emailValid;
	
	@Column(nullable = false, unique = true)
	String externalId;

	String firstName;

	String lastName;


	@Convert(converter = ListConverter.class)
	List<String> otherMembers;

	String phone;

	@Embedded
	Plan plan;

	String type;

}
