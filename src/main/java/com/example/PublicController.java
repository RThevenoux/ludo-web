package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

	public static final String ADMIN_LOGIN_PAGE = "/admin-login";
	public static final String USER_LOGIN_PAGE = "/user-login";

	@RequestMapping(ADMIN_LOGIN_PAGE)
	public String adminLoginPage() {
		return "admin-login";
	}

	@RequestMapping("games")
	public String game() {
		return "game";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(USER_LOGIN_PAGE)
	public String userLoginPage() {
		return "user-login";
	}

}