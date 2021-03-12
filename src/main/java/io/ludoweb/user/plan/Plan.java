package io.ludoweb.user.plan;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Plan {

	String name;

	boolean subscriptionPaid;

	String startDate;

	String endDate;

	int maxBorrowingCount;

	int maxBorrowingDuration;
}
