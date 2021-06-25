package com.pedro.sistemapedido.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pedro.sistemapedido.model.MyUserDetails;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView index(@AuthenticationPrincipal MyUserDetails user) {
		ModelAndView view = new ModelAndView("index");
		
		return view.addObject("user", user);
	}
}
