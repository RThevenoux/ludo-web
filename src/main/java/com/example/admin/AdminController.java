package com.example.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.user.UserInput;
import com.example.user.UserService;

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
	public String user(Map<String, Object> model) {
		List<UserInput> users = userService.list();
		model.put("users", users);
		return "admin-user";
	}

}
