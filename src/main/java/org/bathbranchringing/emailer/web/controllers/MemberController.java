package org.bathbranchringing.emailer.web.controllers;

import java.util.Date;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Membership;
import org.bathbranchringing.emailer.core.domain.Subscriber;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.bathbranchringing.emailer.core.repo.UserDAO;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/towers/{boardId}", "/groups/{boardId}"})
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class MemberController extends BaseController {
    
    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
    
    private static final String PAGE_MEMBER_INFORMATION = "/pages/memberInformation";

    @Autowired
    private BoardDAO boardDAO;
    
    @Autowired
    private UserDAO userDAO;
	
    @RequestMapping("/members")
    public String memberPage(@PathVariable final String boardId,
                             final ModelMap model) {
        
        final Board board = initialise(model, boardId);
        if (board == null) {
            return REDIRECT_HOME;
        } else {
            return PAGE_MEMBER_INFORMATION;
        }
    }
    
    @RequestMapping(value = "/members", method = RequestMethod.POST)
    public String addMember(@PathVariable final String boardId,
                            @RequestParam(value = "action") final String action,
                            final ModelMap model) {

        final Board board = initialise(model, boardId);
        final User loggedInUser = getUser();

        if ((board == null) || (loggedInUser == null)) {
            return REDIRECT_HOME;
        }
        
        final User user = userDAO.find(loggedInUser.getId());

        Hibernate.initialize(user.getSubscriptions());
        Hibernate.initialize(user.getMembership());

        final List<Subscriber> subscribers = user.getSubscriptions();
        final List<Membership> membership = user.getMembership();

        if (action.equals("subscribe") || action.equals("unsubscribe")) {
            
            Subscriber subscriber = null;
            for (Subscriber s : subscribers) {
                if (s.getUser().getId() == user.getId()) {
                    subscriber = s;
                    break;
                }
            }
            if (action.equals("subscribe") && (subscriber == null)) {
                subscriber = new Subscriber();
                subscribers.add(subscriber);
                subscriber.setUser(user);
                subscriber.setBoard(board);                
            } else if (action.equals("unsubscribe") && (subscriber != null)) {
                subscribers.remove(subscriber);
            }
            
        } else if (action.equals("join") || action.equals("leave")) {

            Membership member = null;
            for (Membership m : membership) {
                if (m.getBoard().getId() == board.getId()) {
                    member = m;
                    break;
                }
            }
            if (action.equals("join") && (member == null)) {
                member = new Membership();
                membership.add(member);
                member.setUser(user);
                member.setBoard(board);
                member.setRole(null);
            } else if (action.equals("leave") && (member != null)) {
                membership.remove(member);
            }
            
        }
        
        userDAO.update(user);

        return "redirect:members";
        
    }
    
    @RequestMapping(value = "/members/{userId}")
    public String approveMember(@PathVariable final String boardId,
                                @PathVariable final long userId,
                                @RequestParam(required = false, defaultValue = "false") final boolean approve,
                                final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return REDIRECT_HOME;
        }

        List<Membership> membership = board.getMembers();

        Membership member = null;
        for (Membership m : membership) {
            if (m.getUser().getId() == userId) {
                member = m;
                break;
            }
        }
        
        if (member != null) {
            final User user = getUser();
            if (board.isMember(user)) {
                if (approve) {
                    member.setApprovedBy(user);
                    member.setJoined(new Date());
                } else {
                    membership.remove(member);
                }
                boardDAO.update(board);
            }
        }
        
        return "redirect:../members";
    }
    
}
