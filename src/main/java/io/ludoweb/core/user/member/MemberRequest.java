package io.ludoweb.core.user.member;

import java.util.List;

import lombok.Data;

@Data
public class MemberRequest {

	// If true : Subscription endDate before 'now' AND startDate after 'now'
	// If false : negate previous condition
	Boolean active;

	Boolean emailValid;

	// If not empty and not null, Member must match at least one of this plan.
	List<String> plans;
	// If not empty and not null, Member must match at least one of this type.
	List<String> types;
}
