package org.bathbranchringing.emailer.controllers;

import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/towers")
public class TowerListController {
	
	@Autowired
	private TowerDAO towerDAO;
	
	@RequestMapping({"/", ""})
	public String towersPage(@RequestParam(required = false) final String search,
						     final ModelMap model) {
	    
	    if (search != null) {
	        model.addAttribute("towers", towerDAO.search(search));
	    }

		return "/pages/towerList";
	}
    
}
