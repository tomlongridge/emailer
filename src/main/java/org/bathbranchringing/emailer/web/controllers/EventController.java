package org.bathbranchringing.emailer.web.controllers;

import java.util.Date;

import org.bathbranchringing.emailer.core.domain.Event;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.EventDAO;
import org.bathbranchringing.emailer.web.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/" + URLConstants.BOARD_EVENTS)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class EventController extends BaseController {
    
    @Autowired
    private EventDAO eventDAO;
	
	@RequestMapping("/{noticeId}")
	public String viewNotice(@PathVariable final long noticeId) {
		
	    final Event notice = eventDAO.find(noticeId);
        
        if (notice != null) {
            return String.format("redirect:/%1$s/%2$s/diary/%3$tY/%3$tm/%3$td/%4$d",
                                 notice.getBoard().isGroup() ? "groups" : "towers",
                                 notice.getBoard().getIdentifier(),
                                 notice.getCreationDate(),
                                 noticeId);
        } else {
            return redirect("/" + URLConstants.HOME);
        }
	}
    
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String newNotice(@ModelAttribute("notice") final Notice notice,
                            final BindingResult bindingResult,
                            final ModelMap model) {
        
        User user = getUser();
        
        notice.setCreatedBy(user);
        notice.setCreationDate(new Date());
        notice.setLastModifiedBy(user);
        notice.setModificationDate(notice.getCreationDate());
        
        final Long id = eventDAO.add((Event) notice);
        return redirect("/" + URLConstants.BOARD_EVENTS + "/" + id); 
    }
    
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.POST)
    public String editNotice(@PathVariable final long noticeId,
                             @ModelAttribute("notice") final Event notice,
                             final BindingResult bindingResult,
                             final ModelMap model) {
        
        final Event originalEvent = eventDAO.find(noticeId);
        originalEvent.setHeading(notice.getHeading());
        originalEvent.setContent(notice.getContent());
        if (!StringUtils.isEmpty(notice.getLink())) {
            originalEvent.setLink(notice.getLink());
        }
        originalEvent.setStartDate(notice.getStartDate());
        originalEvent.setEndDate(notice.getEndDate());
        
        eventDAO.update(originalEvent);
        return redirect("/" + URLConstants.BOARD_EVENTS + "/" + noticeId); 
    }
    
}
