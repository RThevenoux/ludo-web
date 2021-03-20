package io.ludoweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.ludoweb.core.user.UserService;
import io.ludoweb.core.user.UserView;
import io.ludoweb.core.user.security.MyUserPrincipal;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("home")
	public ModelAndView home() {
		MyUserPrincipal principal = UserSecurityTool.getPrincipal();
		UserView user = userService.findById(principal.getId()).orElse(null);
		return new ModelAndView("user/home", "user", user);
	}

}
