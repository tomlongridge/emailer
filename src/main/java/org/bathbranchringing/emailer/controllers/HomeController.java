package org.bathbranchringing.emailer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping({"/", "/home"})
	public String init(final ModelMap model) {
		return "home";
	}
}
