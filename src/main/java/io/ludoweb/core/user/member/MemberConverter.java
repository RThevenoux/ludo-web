package io.ludoweb.core.user.member;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import io.ludoweb.core.borrowing.BorrowingConverter;
import io.ludoweb.core.util.Converter;

@Component
public class MemberConverter implements Converter<MemberEntity, MemberView> {

	@Autowired
	BorrowingConverter borrowingConverter;

	@Override
	public MemberView apply(MemberEntity entity) {
		if (entity == null) {
			return null;
		}

		boolean isPassword = !StringUtils.isEmpty(entity.getPassword());

		MemberView view = new MemberView();
		view.setActive(isActive(entity));
		view.setBorrowings(borrowingConverter.convert(entity.getBorrowings()));
		view.setExternalId(entity.getExternalId());
		view.setEmail(entity.getEmail());
		view.setEmailValid(entity.isEmailValid());
		view.setFirstName(entity.getFirstName());
		view.setLastName(entity.getLastName());
		view.setOtherMembers(entity.getOtherMembers());
		view.setPassword(isPassword);
		view.setPhone(entity.getPhone());
		view.setPlan(entity.getPlan());
		view.setType(entity.getType());
		view.setUsername(entity.getUsername());
		return view;
	}

	private boolean isActive(MemberEntity entity) {
		LocalDate now = LocalDate.now();
		
		LocalDate startDate = entity.getPlan().getStartDate();
		if (startDate != null && startDate.isAfter(now)) {
			return false;
		}
		
		LocalDate endDate = entity.getPlan().getEndDate();
		if (endDate != null && endDate.isBefore(now)) {
			return false;
		}
		return true;
	}

}
