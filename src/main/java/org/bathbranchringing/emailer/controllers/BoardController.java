package org.bathbranchringing.emailer.controllers;

import org.bathbranchringing.emailer.core.domain.Tower;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	@Autowired
	private TowerDAO towerDAO;
	
	@RequestMapping("/towers/{id}")
	public String init(@PathVariable final long id, final ModelMap model) {
		
		final Tower tower = towerDAO.find(id);
		
		if (tower == null) {
			return "redirect:/pages/home";
		}
		
		model.addAttribute("tower", tower);
		return "/pages/tower";
	}
}
