package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;

public class BaseController {
    
    private static final String REDIRECT = "redirect:";

	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private BoardDAO boardDAO;

    protected Board initialise(final ModelMap model, final long boardId) {
        
        LOG.debug("Initialising model from board ID \"{}\"", boardId);
        final Board board = boardDAO.find(boardId);
        initialise(model, board);
        return board;
    }

    protected Board initialise(final ModelMap model, final String boardId) {
        
        LOG.debug("Initialising model from board ID \"{}\"", boardId);
        final Board board = boardDAO.find(boardId);
        initialise(model, board);
        return board;
    }
    
    protected void initialise(final ModelMap model, final Board board) {
        
        final User user = getUser();
        if (user != null) {
            LOG.debug("Initialising model for user \"{}\"", user.getEmailAddress());
            model.addAttribute("user", getUser());
        }
        if (board != null) {
            LOG.debug("Initialising model for board \"{}\"", board.getIdentifier());
            model.addAttribute("board", board);
            Hibernate.initialize(board.getMembers());
            Hibernate.initialize(board.getSubscribers());
        }
    }
    
    protected User getUser() {
        
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            final Object userObj = authentication.getPrincipal();
            if (userObj instanceof User) {
                return (User) userObj;
            }
        }
        
        return null;
    }
    
    public static String redirect(final String page) {
    	return REDIRECT + page;
    }
    
}
