package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.web.URLConstants;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController extends BaseController {
	
	private static final String PAGE_LOGIN = "/pages/login";

	@RequestMapping("/" + URLConstants.LOGIN)
	public String init(final ModelMap model) {
		return PAGE_LOGIN;
	}
	
	@RequestMapping(value = "/" + URLConstants.LOGIN, method = RequestMethod.POST)
	public void loggedIn() {
	    MDC.put("user", getUser().getEmailAddress());
	}
}
