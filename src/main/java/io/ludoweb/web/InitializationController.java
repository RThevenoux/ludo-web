package io.ludoweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.ludoweb.core.initialization.InitializationInput;
import io.ludoweb.core.initialization.InitializationService;

@Controller
public class InitializationController {

	public static final String INIT_PATH = "/init";

	@Autowired
	InitializationService service;

	@PostMapping(INIT_PATH)
	public RedirectView doInitailization(@ModelAttribute InitializationInput input) {
		if (!service.isInitialized()) {
			service.doInitialization(input);
		}

		return new RedirectView("/");
	}

	@GetMapping(INIT_PATH)
	public ModelAndView showInitilizationPage() {
		if (service.isInitialized()) {
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("init/home");
		}
	}

}
