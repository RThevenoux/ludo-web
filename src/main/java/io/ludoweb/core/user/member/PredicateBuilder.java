package io.ludoweb.core.user.member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import io.ludoweb.core.user.member.plan.QPlan;

@Component
public class PredicateBuilder {

	private List<Predicate> buildConditions(MemberRequest request) {
		List<Predicate> conditions = new ArrayList<>();

		if (request == null) {
			return conditions;
		}

		if (request.getActive() != null) {
			LocalDate now = LocalDate.now();

			QPlan plan = QMemberEntity.memberEntity.plan;

			BooleanExpression predicate = plan.startDate.before(now).and(plan.endDate.after(now));
			if (request.getActive() == false) {
				predicate = predicate.not();
			}
			conditions.add(predicate);
		}

		if (request.getEmailValid() != null) {
			BooleanExpression predicate = QMemberEntity.memberEntity.emailValid.eq(request.getEmailValid());
			conditions.add(predicate);
		}

		return conditions;
	}

	public Predicate buildPredicate(MemberRequest request) {
		List<Predicate> conditions = buildConditions(request);

		if (conditions.isEmpty()) {
			return getAllPredicate();
		}

		return ExpressionUtils.allOf(conditions);
	}

	private Predicate getAllPredicate() {
		return Expressions.asBoolean(true).isTrue();
	}
}
