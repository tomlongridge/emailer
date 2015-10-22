package org.bathbranchringing.emailer.web.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.NotificationType;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.bathbranchringing.emailer.core.repo.NotificationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/notices")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class NoticeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);
    
    @Autowired
    private BoardDAO boardDAO;
    
    @Autowired
    private NoticeDAO noticeDAO;
    
    @Autowired
    private NotificationDAO notificationDAO;
	
	@RequestMapping("/{noticeId}")
	public String viewNotice(@PathVariable final long noticeId) {

        LOG.debug("-> View Notice /{}", noticeId);

        LOG.debug("Retrieving notice");
	    final Notice notice = noticeDAO.find(noticeId);
	    if (notice == null) {
	        return REDIRECT_HOME;
	    }
	    
        LOG.debug("<- View Notice: /{}", noticeId);
	    return String.format("redirect:/%1$s/%2$s/notices/%3$tY/%3$tm/%3$td/%4$d",
	                         notice.getBoard().isGroup() ? "groups" : "towers",
	                         notice.getBoard().getIdentifier(),
                             notice.getCreationDate(),
                             noticeId);
	}
    
    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String newNotice(@ModelAttribute("notice") final Notice notice,
                            final BindingResult bindingResult,
                            final ModelMap model) {

        LOG.debug("-> New Notice");
        
        final User user = getUser();
        
        notice.setCreatedBy(user);
        notice.setCreationDate(new Date());
        notice.setLastModifiedBy(user);
        notice.setModificationDate(notice.getCreationDate());

        LOG.debug("Persisting new notice details");
        final Long noticeId = noticeDAO.add(notice);
        notificationDAO.add(notice, NotificationType.CREATION);

        LOG.debug("<- New Notice: /{}", noticeId);
        return "redirect:/notices/" + noticeId; 
        
    }
    
    @RequestMapping(value = "/{noticeId}", method = RequestMethod.POST)
    public String editNotice(@PathVariable final long noticeId,
                             @ModelAttribute("notice") @Valid final Notice notice,
                             final BindingResult bindingResult,
                             final ModelMap model) {

        LOG.debug("-> Edit Notice: /{}", noticeId);
        
        final Board board = initialise(model, notice.getBoard().getId());
        if (board == null) {
            return REDIRECT_HOME;
        }
        
        if (!board.isAdmin(getUser())) {
            LOG.warn("Unauthorised edit attempted, user:{}", getUser());
            return REDIRECT_HOME;
        }
        
        final Notice originalNotice = noticeDAO.find(noticeId);
        model.addAttribute("notice", notice);
        
        if (bindingResult.hasErrors()) {
            LOG.debug("{} validation error(s) in notice", bindingResult.getErrorCount());
            notice.setCreatedBy(originalNotice.getCreatedBy());
            notice.setCreationDate(originalNotice.getCreationDate());
            notice.setLastModifiedBy(originalNotice.getLastModifiedBy());
            notice.setModificationDate(originalNotice.getModificationDate());
            return "/pages/editNotice";
        } else {
            LOG.debug("Retrieving edited notice");

            LOG.debug("Persisting updated notice details");
            originalNotice.setHeading(notice.getHeading());
            originalNotice.setContent(notice.getContent());
            originalNotice.setLink(notice.getLink());
            noticeDAO.update(originalNotice);
            
            LOG.debug("Adding modification notification");
            notificationDAO.add(originalNotice, NotificationType.MODIFICATION);
            
            LOG.debug("<- Edit Notice: /{}", noticeId);
            return "redirect:/notices/" + noticeId; 
        }
        
    }
	
}
