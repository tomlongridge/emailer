package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.web.URLConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController {
	
	private static final String PAGE_HOME = "/pages/home";

	@RequestMapping({"/", "/" + URLConstants.HOME})
	public String init(final ModelMap model) {
		return PAGE_HOME;
	}
}
