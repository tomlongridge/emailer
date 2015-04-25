package org.bathbranchringing.emailer.controllers;

import java.util.Date;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notices")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class NoticeController {
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping("/{noticeId}")
	public String viewNotice(@PathVariable final long noticeId) {
		
	    final Notice notice = noticeDAO.find(noticeId);
	    
	    if (notice != null) {
    	    return String.format("redirect:/%1$s/%2$s/notices/%3$tY/%3$tm/%3$td/%4$d",
    	                         notice.getBoard().isGroup() ? "groups" : "towers",
    	                         notice.getBoard().getIdentifier(),
                                 notice.getCreationDate(),
                                 noticeId);
	    } else {
	        return "redirect:/home";
	    }
	}
    
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String newNotice(@ModelAttribute("notice") final Notice notice,
                            final BindingResult bindingResult,
                            final ModelMap model) {
        
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        notice.setCreatedBy(user);
        notice.setCreationDate(new Date());
        notice.setLastModifiedBy(user);
        notice.setModificationDate(notice.getCreationDate());
        
        final Long id = noticeDAO.add(notice);
        return "redirect:/notices/" + id; 
        
    }
    
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.POST)
    public String editNotice(@PathVariable final long noticeId,
                             @ModelAttribute("notice") final Notice notice,
                             final BindingResult bindingResult,
                             final ModelMap model) {
        
        final Notice originalNotice = noticeDAO.find(noticeId);
        originalNotice.setHeading(notice.getHeading());
        originalNotice.setContent(notice.getContent());
        if (!StringUtils.isEmpty(notice.getLink())) {
            originalNotice.setLink(notice.getLink());
        }
        noticeDAO.update(originalNotice);
        return "redirect:/notices/" + noticeId; 
    }
	
}
