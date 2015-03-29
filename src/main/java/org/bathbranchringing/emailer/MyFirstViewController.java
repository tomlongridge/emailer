package org.bathbranchringing.emailer;

import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyFirstViewController {
	
	@Autowired
	private TowerDAO towerDAO;

	@ModelAttribute("thetitle")
	public String getTitle() {
		return towerDAO.find(1L).getCounty().getCountry().getName();
	}
	
	@RequestMapping({"/hi","/"})
	public String hello() {
		return "test";
	}
}
