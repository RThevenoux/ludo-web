package io.ludoweb.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.ludoweb.core.borrowing.BorrowingService;
import io.ludoweb.core.config.ConfigService;
import io.ludoweb.core.config.ConfigView;
import io.ludoweb.core.user.admin.AdminCredentialsInput;
import io.ludoweb.core.user.admin.AdminUserService;
import io.ludoweb.core.user.member.PasswordWrapper;
import io.ludoweb.core.user.member.MemberService;
import io.ludoweb.core.user.member.MemberStats;
import io.ludoweb.core.user.member.MemberView;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	AdminUserService adminUserService;
	@Autowired
	BorrowingService borrowingService;
	@Autowired
	ConfigService configService;
	@Autowired
	MemberService memberService;

	@GetMapping("config")
	public RedirectView showConfig() {
		return new RedirectView("/admin/config/appearance");
	}

	@GetMapping("config/appearance")
	public ModelAndView showConfigAppaerance() {
		ConfigView config = configService.getConfig();

		ModelAndView modelAndView = new ModelAndView("admin/config/appearance");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", false);

		return modelAndView;
	}

	@GetMapping("config/security")
	public ModelAndView showConfigSecurity() {
		ModelAndView modelAndView = new ModelAndView("admin/config/security");
		modelAndView.addObject("updateOk", false);

		return modelAndView;
	}

	@RequestMapping("home")
	public ModelAndView showHome() {
		boolean subscriptionPaid = true;
		MemberStats memberStats = memberService.getMemberStats(subscriptionPaid);
		long borrowingCount = borrowingService.getActiveBorrowingCount();

		ModelAndView modelAndView = new ModelAndView("admin/home");
		modelAndView.addObject("memberStats", memberStats);
		modelAndView.addObject("borrowingCount", borrowingCount);

		return modelAndView;
	}

	@RequestMapping("mail")
	public ModelAndView showMailingList() {
		return showHome();
	}

	@RequestMapping("stats")
	public ModelAndView showStatistics() {
		return showHome();
	}

	@RequestMapping("member")
	public ModelAndView showMembers() {
		List<MemberView> members = memberService.list();
		return new ModelAndView("admin/members", "members", members);
	}

	@GetMapping("member/{externalId}/password")
	public ModelAndView showMemberPasswordFrom(@PathVariable String externalId) {
		return memberService.findByExternalId(externalId)//
				.map(member -> new ModelAndView("admin/member-password", "member", member))//
				.orElseGet(() -> new ModelAndView("redirect:/admin/members"));
	}

	@PostMapping("member/{externalId}/password")
	public ModelAndView submitMEmberPasswordForm(@PathVariable String externalId,
			@ModelAttribute PasswordWrapper password) {
		if (password == null || StringUtils.isEmpty(password.getPassword())) {
			return showMemberPasswordFrom(externalId);
		}

		boolean memberFound = memberService.updateMemberPassword(externalId, password.getPassword());

		if (memberFound) {
			return new ModelAndView("admin/member-password-ok");
		} else {
			return new ModelAndView("error", "message", "Utilisateur non trouvé: " + externalId);
		}
	}

	@PostMapping("config/admin-credetials")
	public ModelAndView updateAdminCredential(@ModelAttribute AdminCredentialsInput input) {
		adminUserService.createOrUpdateAdminUser(input);

		ModelAndView modelAndView = new ModelAndView("admin/config/security");
		modelAndView.addObject("updateOk", true);

		return modelAndView;
	}

	@PostMapping("config/appearance")
	public ModelAndView updateConfigAppearance(@ModelAttribute ConfigView input) {
		ConfigView config = configService.updateConfig(input);

		ModelAndView modelAndView = new ModelAndView("admin/config/appearance");
		modelAndView.addObject("config", config);
		modelAndView.addObject("updateOk", true);

		return modelAndView;
	}

}
