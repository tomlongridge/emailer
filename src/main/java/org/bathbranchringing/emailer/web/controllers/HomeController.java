package org.bathbranchringing.emailer.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController {
	
	@RequestMapping({"/", "/home"})
	public String init(final ModelMap model) {
		return "/pages/home";
	}
}
