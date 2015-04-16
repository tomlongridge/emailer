package org.bathbranchringing.emailer.controllers;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notices")
public class NoticeController {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping("/{noticeId}")
	public String viewNotice(@PathVariable final long noticeId) {
		
	    final Notice notice = noticeDAO.find(noticeId);
	    
	    return String.format("redirect:/%1$s/%2$s/notices/%3$tY/%3$tm/%3$td/%4$d",
	                         notice.getBoard().isGroup() ? "groups" : "towers",
	                         notice.getBoard().getIdentifier(),
                             notice.getCreationDate(),
                             noticeId);
	}
    
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.POST)
    public String noticePage(@PathVariable final long noticeId,
                             @ModelAttribute("notice") final Notice notice,
                             final BindingResult bindingResult,
                             final ModelMap model) {
        
        final Notice originalNotice = noticeDAO.find(noticeId);
        originalNotice.setHeading(notice.getHeading());
        originalNotice.setContent(notice.getContent());
        noticeDAO.update(originalNotice);
        return "redirect:/notices/" + noticeId; 
    }
	
}
