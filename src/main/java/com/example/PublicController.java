package com.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

	@Autowired
	TickDAO tickDao;

	@RequestMapping("/")
	String index() {
		return "index";
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

}
