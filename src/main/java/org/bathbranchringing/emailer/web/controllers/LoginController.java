package org.bathbranchringing.emailer.web.controllers;

import org.jboss.logging.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {
	
	@RequestMapping("/login")
	public String init(final ModelMap model) {
		return "/pages/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void loggedIn() {
	    MDC.put("user", getUser().getEmailAddress());
	}
}
