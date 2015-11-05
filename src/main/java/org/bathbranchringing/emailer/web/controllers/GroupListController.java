package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.core.repo.GroupDAO;
import org.bathbranchringing.emailer.web.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/" + URLConstants.GROUPS)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class GroupListController extends BaseController {
	
	private static final String PAGE_GROUP_LIST = "/pages/groupList";
	@Autowired
	private GroupDAO groupDAO;
	
	@RequestMapping({"/", ""})
	public String groupsPage(@RequestParam(required = false) final String search,
						      final ModelMap model) {
	    
	    if (search != null) {
	        model.addAttribute("groups", groupDAO.search(search));
	    }

		return PAGE_GROUP_LIST;
	}
    
}
