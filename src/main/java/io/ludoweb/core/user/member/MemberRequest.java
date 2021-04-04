package io.ludoweb.core.user.member;

import lombok.Data;

@Data
public class MemberRequest {

	/* Subscription endDate before 'now' AND startDate after 'now' */
	Boolean active;

	Boolean emailValid;
}
