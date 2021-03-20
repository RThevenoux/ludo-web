package io.ludoweb.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ludoweb.admin.user.AdminUserService;

@Service
public class InitializationService {

	@Autowired
	AdminUserService adminUserService;

	public void doInitialization(InitializationInput input) {
		adminUserService.createOrUpdateAdminUser(input.getAdminCredentials());
	}

	public boolean isInitialized() {
		return adminUserService.isAdminUser();
	}
}
