package io.ludoweb.core.user.member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

import io.ludoweb.core.user.member.plan.QPlan;

@Component
public class PredicateBuilder {

	private static QMemberEntity member = QMemberEntity.memberEntity;

	private Optional<BooleanExpression> active(Boolean active) {
		if (active == null) {
			return Optional.empty();
		}

		LocalDate now = LocalDate.now();

		QPlan plan = member.plan;

		BooleanExpression predicate = plan.startDate.before(now).and(plan.endDate.after(now));
		if (active == false) {
			return Optional.of(predicate.not());
		} else {
			return Optional.of(predicate);
		}
	}

	private List<Predicate> buildConditions(MemberRequest request) {
		List<Predicate> conditions = new ArrayList<>();

		if (request == null) {
			return conditions;
		}

		active(request.getActive()).ifPresent(conditions::add);
		emailValid(request.getEmailValid()).ifPresent(conditions::add);
		plans(request.getPlans()).ifPresent(conditions::add);
		types(request.getTypes()).ifPresent(conditions::add);

		return conditions;
	}

	public Predicate buildPredicate(MemberRequest request) {
		List<Predicate> conditions = buildConditions(request);

		if (conditions.isEmpty()) {
			return getAllPredicate();
		}

		return ExpressionUtils.allOf(conditions);
	}

	private Optional<BooleanExpression> emailValid(Boolean emailValid) {
		if (emailValid == null) {
			return Optional.empty();
		}

		BooleanExpression predicate = member.emailValid.eq(emailValid);
		return Optional.of(predicate);
	}

	private Predicate getAllPredicate() {
		return Expressions.asBoolean(true).isTrue();
	}

	private Optional<BooleanExpression> plans(List<String> plans) {
		if (plans == null || plans.isEmpty()) {
			return Optional.empty();
		}
		BooleanExpression predicate = member.plan.name.in(plans);
		return Optional.of(predicate);
	}

	private Optional<BooleanExpression> types(List<String> types) {
		if (types == null || types.isEmpty()) {
			return Optional.empty();
		}
		BooleanExpression predicate = member.type.in(types);
		return Optional.of(predicate);
	}
}
