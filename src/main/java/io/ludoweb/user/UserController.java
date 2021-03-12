package io.ludoweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.ludoweb.user.security.MyUserPrincipal;
import io.ludoweb.user.security.UserSecurityTool;

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
