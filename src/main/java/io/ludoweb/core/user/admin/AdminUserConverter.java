package io.ludoweb.core.user.admin;

import org.springframework.stereotype.Component;

import io.ludoweb.core.util.Converter;

@Component
public class AdminUserConverter implements Converter<AdminUserEntity, AdminUserView> {

	@Override
	public AdminUserView apply(AdminUserEntity entity) {
		AdminUserView view = new AdminUserView();
		view.setId(entity.getId());
		view.setRole(entity.getRole());
		view.setUsername(entity.getUsername());
		return view;
	}

}
