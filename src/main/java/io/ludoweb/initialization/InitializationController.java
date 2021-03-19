package io.ludoweb.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class InitializationController {

	@Autowired
	InitializationService service;

	@PostMapping("/init")
	public RedirectView doInitailization(@ModelAttribute InitializationInput input) {
		if (!service.isInitialized()) {
			service.doInitialization(input);
		}

		return new RedirectView("/");
	}

	@GetMapping("/init")
	public ModelAndView showInitilizationPage() {
		if (service.isInitialized()) {
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("init/home");
		}
	}

}
