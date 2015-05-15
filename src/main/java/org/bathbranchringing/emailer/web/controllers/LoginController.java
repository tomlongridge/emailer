package org.bathbranchringing.emailer.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String init(final ModelMap model) {
		return "/pages/login";
	}
}
