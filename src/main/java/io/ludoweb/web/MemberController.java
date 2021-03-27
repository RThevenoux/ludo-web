package io.ludoweb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import io.ludoweb.core.user.member.MemberUserDetails;
import io.ludoweb.core.user.member.MemberService;
import io.ludoweb.core.user.member.MemberView;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@RequestMapping("home")
	public ModelAndView home() {
		MemberView member = getMemberView();
		return new ModelAndView("member/home", "member", member);
	}

	private MemberView getMemberView() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();
		return memberService.findById(principal.getId()).orElse(null);
	}

}
