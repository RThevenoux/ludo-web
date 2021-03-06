package io.ludoweb.core.user.member;

import java.util.List;

import io.ludoweb.core.borrowing.BorrowingView;
import io.ludoweb.core.user.member.plan.Plan;
import lombok.Data;

@Data
public class MemberView {

	boolean active;
	
	List<BorrowingView> borrowings;

	String email;

	boolean emailValid;

	String externalId;

	String firstName;

	String lastName;

	List<String> otherMembers;

	boolean password;

	String phone;

	Plan plan;

	String type;

	String username;
}
