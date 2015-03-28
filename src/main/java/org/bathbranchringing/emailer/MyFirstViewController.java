package org.bathbranchringing.emailer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyFirstViewController {

	@RequestMapping("/hi")
	public ModelAndView hello() {
		return new ModelAndView("showMessage", "message", "Hello World");
	}
}
