package io.ludoweb.core.user.member.plan;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Plan {

	LocalDate endDate;

	int maxBorrowingCount;

	int maxBorrowingDuration;

	String name;

	LocalDate startDate;

	Boolean subscriptionPaid;
}
