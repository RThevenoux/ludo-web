package io.ludoweb.admin;

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

import io.ludoweb.borrowing.BorrowingService;
import io.ludoweb.config.ConfigService;
import io.ludoweb.config.ConfigView;
import io.ludoweb.user.PasswordWrapper;
import io.ludoweb.user.UserService;
import io.ludoweb.user.UserStats;
import io.ludoweb.user.UserView;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	BorrowingService borrowingService;
	@Autowired
	ConfigService configService;
	@Autowired
	UserService userService;

	@GetMapping("config")
	public ModelAndView showConfig() {
		ConfigView config = configService.getConfig();

		ModelAndView modelAndView = new ModelAndView("admin/config");
		modelAndView.addObject("config", config);
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

	@PostMapping("config")
	public ModelAndView updateConfig(@ModelAttribute ConfigView input) {
		ConfigView config = configService.updateConfig(input);

		ModelAndView modelAndView = new ModelAndView("admin/config");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", true);

		return modelAndView;
	}

}
