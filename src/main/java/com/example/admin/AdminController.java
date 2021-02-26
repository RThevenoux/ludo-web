package com.example.admin;

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

import com.example.user.PasswordWrapper;
import com.example.user.UserService;
import com.example.user.UserView;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	UserService userService;

	@RequestMapping("home")
	public String index() {
		return "admin-home";
	}

	@RequestMapping("user")
	public ModelAndView user() {
		List<UserView> users = userService.list();
		return new ModelAndView("admin-user","users",users);
	}

	@GetMapping("user/{username}/password")
	public ModelAndView showUserPasswordFrom(@PathVariable String username) {
		UserView user = userService.get(username);
		if (user == null) {
			return new ModelAndView("redirect:/admin/user");
		}

		return new ModelAndView("admin-user-password", "user", user);
	}

	@PostMapping("user/{username}/password")
	public ModelAndView submitUserPasswordForm(@PathVariable String username,
			@ModelAttribute PasswordWrapper password) {
		if (password == null || StringUtils.isEmpty(password.getPassword())) {
			return showUserPasswordFrom(username);
		}

		UserView user = userService.updateUserPassword(username, password.getPassword());

		if (user == null) {
			return new ModelAndView("error", "message", "Utilisateur non trouvé:" + username);
		}

		return new ModelAndView("admin-user-password-ok");

	}

}
