package com.example.user;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

	@RequestMapping("home")
	public String test(Map<String, Object> model) {
		model.put("user", getUser());
		return "user-home";
	}

	private UserEntity getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MyUserPrincipal principal = (MyUserPrincipal) auth.getPrincipal();
		return principal.getUser();
	}

}
