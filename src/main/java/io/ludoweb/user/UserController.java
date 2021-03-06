package io.ludoweb.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.ludoweb.user.security.UserSecurityTool;

@Controller
@RequestMapping("user")
public class UserController {

	@RequestMapping("home")
	public ModelAndView home() {
		return new ModelAndView("user/home", "user", UserSecurityTool.getUser());
	}

}
