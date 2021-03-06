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

import io.ludoweb.config.ConfigView;
import io.ludoweb.config.ConfigService;
import io.ludoweb.user.PasswordWrapper;
import io.ludoweb.user.UserService;
import io.ludoweb.user.UserStats;
import io.ludoweb.user.UserView;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UserService userService;
	@Autowired
	ConfigService configService;

	@RequestMapping("home")
	public ModelAndView showHome() {
		boolean subscriptionPaid = true;
		UserStats userStats = userService.getUserStats(subscriptionPaid);

		long borrowingCount = userService.getBorrowingCount();

		ModelAndView modelAndView = new ModelAndView("admin/home");
		modelAndView.addObject("userStats", userStats);
		modelAndView.addObject("borrowingCount", borrowingCount);

		return modelAndView;
	}

	@GetMapping("config")
	public ModelAndView showConfig() {
		ConfigView config = configService.getConfig();

		ModelAndView modelAndView = new ModelAndView("admin/config");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", false);

		return modelAndView;
	}

	@PostMapping("config")
	public ModelAndView updateConfig(@ModelAttribute ConfigView input) {
		ConfigView config = configService.updateConfig(input);
		
		ModelAndView modelAndView = new ModelAndView("admin/config");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", true);

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

	@GetMapping("user/{username}/password")
	public ModelAndView showUserPasswordFrom(@PathVariable String username) {
		UserView user = userService.get(username);
		if (user == null) {
			return new ModelAndView("redirect:/admin/user");
		}

		return new ModelAndView("admin/user-password", "user", user);
	}

	@PostMapping("user/{username}/password")
	public ModelAndView submitUserPasswordForm(@PathVariable String username,
			@ModelAttribute PasswordWrapper password) {
		if (password == null || StringUtils.isEmpty(password.getPassword())) {
			return showUserPasswordFrom(username);
		}

		UserView user = userService.updateUserPassword(username, password.getPassword());

		if (user == null) {
			return new ModelAndView("error", "message", "Utilisateur non trouvé: " + username);
		}

		return new ModelAndView("admin/user-password-ok");
	}

}
