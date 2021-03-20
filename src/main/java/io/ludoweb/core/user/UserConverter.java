package io.ludoweb.core.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import io.ludoweb.core.borrowing.BorrowingConverter;
import io.ludoweb.core.util.Converter;

@Component
public class UserConverter implements Converter<UserEntity, UserView> {

	@Autowired
	BorrowingConverter borrowingConverter;

	@Override
	public UserView apply(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		boolean isPassword = !StringUtils.isEmpty(entity.getPassword());

		UserView data = new UserView();
		data.setBorrowings(borrowingConverter.convert(entity.getBorrowings()));
		data.setExternalId(entity.getExternalId());
		data.setFirstName(entity.getFirstName());
		data.setLastName(entity.getLastName());
		data.setMail(entity.getMail());
		data.setOtherMembers(entity.getOtherMembers());
		data.setPassword(isPassword);
		data.setPhone(entity.getPhone());
		data.setPlan(entity.getPlan());
		data.setType(entity.getType());
		data.setUsername(entity.getUsername());
		return data;
	}
}
