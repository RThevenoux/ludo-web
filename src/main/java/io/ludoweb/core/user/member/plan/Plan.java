package io.ludoweb.core.user.member.plan;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Plan {

	String endDate;

	int maxBorrowingCount;

	int maxBorrowingDuration;

	String name;

	String startDate;

	Boolean subscriptionPaid;
}
