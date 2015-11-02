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
@RequestMapping(
        {
            "/" + BoardController.URL_TOWER + "/{boardId}/" + MemberController.URL_MEMBERS,
            "/" + BoardController.URL_GROUP + "/{boardId}/" + MemberController.URL_MEMBERS
        }
)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class MemberController extends BaseController {
    
    public static final String URL_MEMBERS = "members";

    private static final Logger LOG = LoggerFactory.getLogger(MemberController.class);
    
    private static final String PAGE_MEMBER_INFORMATION = "/pages/memberInformation";

    @Autowired
    private BoardDAO boardDAO;
    
    @Autowired
    private UserDAO userDAO;
	
    @RequestMapping("")
    public String memberPage(@PathVariable final String boardId,
                             final ModelMap model) {
        
        LOG.debug("-> View Member Information /{}", boardId);
        
        final Board board = initialise(model, boardId);
        if (board == null) {
            LOG.error("Unable to find board {}", boardId);
            return REDIRECT_HOME;
        }
        
        LOG.debug("<- View Member Information /{}", boardId);
        return PAGE_MEMBER_INFORMATION;
    }
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addMember(@PathVariable final String boardId,
                            @RequestParam(value = "action") final String action,
                            final ModelMap model) {

        LOG.debug("-> Add Member /{}/{} (action = {})", boardId, URL_MEMBERS, action);
        
        final Board board = initialise(model, boardId);
        if (board == null) {
            LOG.error("Unable to find board {}", boardId);
            return REDIRECT_HOME;
        }
        
        final User loggedInUser = getUser();
        if (loggedInUser == null) {
            LOG.debug("User not logged in, redirecting to registration page");
            return REDIRECT_REGISTER;
        }
        
        LOG.debug("Retrieving user {} from database", loggedInUser.getId());
        final User user = userDAO.find(loggedInUser.getId());
        Hibernate.initialize(user.getSubscriptions());
        Hibernate.initialize(user.getMembership());

        final List<Subscriber> subscribers = user.getSubscriptions();
        final List<Membership> membership = user.getMembership();

        if (action.equals("subscribe") || action.equals("unsubscribe")) {
            
            LOG.debug("Subscription modification requested");
            
            Subscriber subscriber = null;
            for (Subscriber s : subscribers) {
                if (s.getUser().getId() == user.getId()) {
                    LOG.debug("User {} already subscribed to board {}", user.getId(), boardId);
                    subscriber = s;
                    break;
                }
            }
            if (action.equals("subscribe") && (subscriber == null)) {
                LOG.debug("Subscribing user {} to board {}", user.getId(), boardId);
                subscriber = new Subscriber();
                subscribers.add(subscriber);
                subscriber.setUser(user);
                subscriber.setBoard(board);                
            } else if (action.equals("unsubscribe") && (subscriber != null)) {
                LOG.debug("Unsubscribing user {} from board {}", user.getId(), boardId);
                subscribers.remove(subscriber);
            }
            
        } else if (action.equals("join") || action.equals("leave")) {

            LOG.debug("Membership modification requested");
            
            Membership member = null;
            for (Membership m : membership) {
                if (m.getBoard().getId() == board.getId()) {
                    LOG.debug("User {} already a member of board {}", user.getId(), boardId);
                    member = m;
                    break;
                }
            }
            if (action.equals("join") && (member == null)) {
                LOG.debug("Joining user {} to board {}", user.getId(), boardId);
                member = new Membership();
                membership.add(member);
                member.setUser(user);
                member.setBoard(board);
                member.setRole(null);
            } else if (action.equals("leave") && (member != null)) {
                LOG.debug("Leaving user {} from board {}", user.getId(), boardId);
                membership.remove(member);
            }
            
        }
        
        LOG.debug("Persisting user changes");
        userDAO.update(user);

        LOG.debug("<- Add Member /{}/{} (action = {})", boardId, URL_MEMBERS, action);
        return "redirect:" + URL_MEMBERS + "?" + action;
        
    }
    
    @RequestMapping(value = "/{userId}")
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
        
        return "redirect:../" + URL_MEMBERS;
    }
    
}
