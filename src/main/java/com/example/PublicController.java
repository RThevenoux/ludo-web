package com.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tick.TickService;

@Controller
public class PublicController {

	public static final String ADMIN_LOGIN_PAGE = "/admin-login";
	public static final String USER_LOGIN_PAGE = "/user-login";

	@Autowired
	TickService tickDao;

	@RequestMapping(ADMIN_LOGIN_PAGE)
	String adminLoginPage() {
		return "admin-login";
	}

	@RequestMapping("/db")
	String db(Map<String, Object> model) {

		try {
			List<String> output = tickDao.loadTicks().stream()//
					.map(tick -> "Read from DB: " + tick.getTimestamp())//
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