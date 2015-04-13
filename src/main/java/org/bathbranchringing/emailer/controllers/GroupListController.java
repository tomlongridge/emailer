package org.bathbranchringing.emailer.controllers;

import org.bathbranchringing.emailer.core.repo.GroupDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/groups")
public class GroupListController {
	
	@Autowired
	private GroupDAO groupDAO;
	
	@RequestMapping({"/", ""})
	public String groupssPage(@RequestParam(required = false) final String search,
						      final ModelMap model) {
	    
	    if (search != null) {
	        model.addAttribute("groups", groupDAO.search(search));
	    }

		return "/pages/groupList";
	}
    
}
