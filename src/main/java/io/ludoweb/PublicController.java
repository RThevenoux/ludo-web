package io.ludoweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import io.ludoweb.game.GameService;
import io.ludoweb.game.GameView;

@Controller
public class PublicController {

	public static final String ADMIN_LOGIN_ACTION = "/admin/login";
	public static final String ADMIN_LOGIN_PAGE = "/public/admin-login";
	public static final String ADMIN_LOGIN_PAGE_LOGIN_FAIL = ADMIN_LOGIN_PAGE + "?loginError=true";
	private static final String ADMIN_LOGIN_TITLE = "Accés réservé aux administrateurs";

	public static final String USER_LOGIN_ACTION = "/user/login";
	public static final String USER_LOGIN_PAGE = "/public/user-login";
	public static final String USER_LOGIN_PAGE_LOGIN_FAIL = USER_LOGIN_PAGE + "?loginError=true";
	private static final String USER_LOGIN_TITLE = "Connection \"Membre\"";

	@Autowired
	GameService gameService;

	@RequestMapping(ADMIN_LOGIN_PAGE)
	public ModelAndView adminLoginPage(@RequestParam(required = false, defaultValue = "false") boolean loginError) {
		return loginModelAndView(loginError, ADMIN_LOGIN_TITLE, ADMIN_LOGIN_ACTION);
	}

	@RequestMapping("games")
	public ModelAndView game() {
		List<GameView> games = gameService.listAll();
		return new ModelAndView("public/games", "games", games);
	}

	@RequestMapping("/")
	public String index() {
		return "public/index";
	}

	private ModelAndView loginModelAndView(boolean loginError, String title, String action) {
		ModelAndView modelAndView = new ModelAndView("public/login");
		modelAndView.addObject("loginError", loginError);
		modelAndView.addObject("title", title);
		modelAndView.addObject("action", action);

		return modelAndView;
	}

	@RequestMapping(USER_LOGIN_PAGE)
	public ModelAndView userLoginPage(@RequestParam(required = false, defaultValue = "false") boolean loginError) {
		return loginModelAndView(loginError, USER_LOGIN_TITLE, USER_LOGIN_ACTION);
	}

}