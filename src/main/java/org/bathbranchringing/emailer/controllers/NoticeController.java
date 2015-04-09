package org.bathbranchringing.emailer.controllers;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notices")
public class NoticeController {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping("/{noticeId}")
	public String towerPage(@PathVariable final long noticeId) {
		
	    final Notice notice = noticeDAO.find(noticeId);
	    
	    return String.format("redirect:/towers/%1$s/notices/%2$tY/%2$tm/%2$td/%3$d",
	                         notice.getTower().getDoveId(),
                             notice.getCreationDate(),
                             noticeId);
	}
	
}
