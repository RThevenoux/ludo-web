package com.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

	public static final String ADMIN_LOGIN_PAGE = "/admin-login";
	public static final String USER_LOGIN_PAGE = "/user-login";

	@Autowired
	TickDAO tickDao;

	@RequestMapping(ADMIN_LOGIN_PAGE)
	String adminLoginPage() {
		return "admin-login";
	}

	@RequestMapping("/db")
	String db(Map<String, Object> model) {

		try {
			List<String> output = tickDao.loadTick().stream().map(tick -> "Read from DB: " + tick)
					.collect(Collectors.toList());

			model.put("records", output);
			return "db";

		} catch (Exception e) {
			model.put("message", e.getMessage());
			return "error";
		}

	}

	@RequestMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping(USER_LOGIN_PAGE)
	String userLoginPage() {
		return "user-login";
	}

}
