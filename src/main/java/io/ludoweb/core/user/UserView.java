package io.ludoweb.core.user;

import java.util.List;

import io.ludoweb.core.borrowing.BorrowingView;
import io.ludoweb.core.user.plan.Plan;
import lombok.Data;

@Data
public class UserView {

	List<BorrowingView> borrowings;

	String externalId;

	String firstName;

	String lastName;

	String mail;

	List<String> otherMembers;

	boolean password;

	String phone;

	Plan plan;

	String type;

	String username;
}
