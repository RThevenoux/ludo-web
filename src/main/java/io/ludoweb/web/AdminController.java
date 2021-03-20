package io.ludoweb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.ludoweb.core.adminuser.AdminCredentialsInput;
import io.ludoweb.core.adminuser.AdminUserService;
import io.ludoweb.core.borrowing.BorrowingService;
import io.ludoweb.core.config.ConfigService;
import io.ludoweb.core.config.ConfigView;
import io.ludoweb.core.user.PasswordWrapper;
import io.ludoweb.core.user.UserService;
import io.ludoweb.core.user.UserStats;
import io.ludoweb.core.user.UserView;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminUserService adminUserService;
	@Autowired
	BorrowingService borrowingService;
	@Autowired
	ConfigService configService;
	@Autowired
	UserService userService;

	@GetMapping("config")
	public RedirectView showConfig() {
		return new RedirectView("/admin/config/appearance");
	}

	@GetMapping("config/appearance")
	public ModelAndView showConfigAppaerance() {
		ConfigView config = configService.getConfig();

		ModelAndView modelAndView = new ModelAndView("admin/config/appearance");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", false);

		return modelAndView;
	}

	@GetMapping("config/security")
	public ModelAndView showConfigSecurity() {
		ModelAndView modelAndView = new ModelAndView("admin/config/security");
		modelAndView.addObject("updateOk", false);

		return modelAndView;
	}

	@RequestMapping("home")
	public ModelAndView showHome() {
		boolean subscriptionPaid = true;
		UserStats userStats = userService.getUserStats(subscriptionPaid);
		long borrowingCount = borrowingService.getActiveBorrowingCount();

		ModelAndView modelAndView = new ModelAndView("admin/home");
		modelAndView.addObject("userStats", userStats);
		modelAndView.addObject("borrowingCount", borrowingCount);

		return modelAndView;
	}

	@RequestMapping("mail")
	public ModelAndView showMailingList() {
		return showHome();
	}

	@RequestMapping("stats")
	public ModelAndView showStatistics() {
		return showHome();
	}

	@RequestMapping("user")
	public ModelAndView showUserList() {
		List<UserView> users = userService.list();
		return new ModelAndView("admin/user", "users", users);
	}

	@GetMapping("user/{externalId}/password")
	public ModelAndView showUserPasswordFrom(@PathVariable String externalId) {
		return userService.findByExternalId(externalId)//
				.map(user -> new ModelAndView("admin/user-password", "user", user))//
				.orElseGet(() -> new ModelAndView("redirect:/admin/user"));
	}

	@PostMapping("user/{username}/password")
	public ModelAndView submitUserPasswordForm(@PathVariable String username,
			@ModelAttribute PasswordWrapper password) {
		if (password == null || StringUtils.isEmpty(password.getPassword())) {
			return showUserPasswordFrom(username);
		}

		boolean userFound = userService.updateUserPassword(username, password.getPassword());

		if (userFound) {
			return new ModelAndView("admin/user-password-ok");
		} else {
			return new ModelAndView("error", "message", "Utilisateur non trouvé: " + username);
		}
	}

	@PostMapping("config/admin-credetials")
	public ModelAndView updateAdminCredential(@ModelAttribute AdminCredentialsInput input) {
		adminUserService.createOrUpdateAdminUser(input);

		ModelAndView modelAndView = new ModelAndView("admin/config/security");
		modelAndView.addObject("updateOk", true);

		return modelAndView;
	}

	@PostMapping("config/appearance")
	public ModelAndView updateConfigAppearance(@ModelAttribute ConfigView input) {
		ConfigView config = configService.updateConfig(input);

		ModelAndView modelAndView = new ModelAndView("admin/config/appearance");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", true);

		return modelAndView;
	}

}
