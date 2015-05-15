package org.bathbranchringing.emailer.web.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Group;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Tower;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.bathbranchringing.emailer.core.repo.CountryDAO;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/towers/{boardId}", "/groups/{boardId}"})
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BoardController {
    
    @Autowired
    private BoardDAO boardDAO;
    
	@Autowired
	private NoticeDAO noticeDAO;
    
    @Autowired
    private CountyDAO countyDAO;
    
    @Autowired
    private CountryDAO countryDAO;
	
	@RequestMapping(value = {"", "/", "/notices"}, method = RequestMethod.GET)
	public String noticesPage(@PathVariable final String boardId,
                              final ModelMap model) {
		
		final Board board = boardDAO.find(boardId);
		if (board == null) {
		    return "redirect:/home";
		}
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
		model.addAttribute("board", board);
		
		final List<Notice> notices = noticeDAO.getBoardNotices(board);
		model.addAttribute("notices", notices);
		
		return "/pages/board";
	}
    
    @RequestMapping("/notices/{year}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year + 1, Calendar.JANUARY, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);

        return "/pages/board";
    }
    
    @RequestMapping("/notices/{year}/{month}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);

        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);
        
        return "/pages/board";
    }
    
    @RequestMapping("/notices/{year}/{month}/{day}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              @PathVariable final int day,
                              final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);

        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, day, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month - 1, day + 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);

        return "/pages/board";
    }
	
	@RequestMapping(value = "/notices/{year}/{month}/{day}/{noticeId}")
	public String noticePage(@PathVariable final String boardId,
                             @PathVariable final long year,
                             @PathVariable final long month,
                             @PathVariable final long day,
                             @PathVariable final long noticeId,
                             @RequestParam(required = false, defaultValue = "false") final boolean edit,
	                         final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);
        
		final Notice notice = noticeDAO.find(noticeId);
		if ((notice == null) || (notice.getBoard().getId() != board.getId())) {
            return "redirect:/home";
		}
		
		model.addAttribute("notice", notice);
	    if (edit) {
	        return "/pages/editNotice";
	    } else {
	        return "/pages/viewNotice";
	    }
		
	}
    
    @RequestMapping("/notices/new")
    public String newNotice(@PathVariable final String boardId,
                              final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);
        
        Notice notice = new Notice();
        notice.setBoard(board);
        model.addAttribute("notice", notice);
        
        return "/pages/editNotice";
    }
    
    @RequestMapping("/information")
    public String infoPage(@PathVariable final String boardId,
                           @RequestParam(required = false, defaultValue = "false") final boolean edit,
                           final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }

        Hibernate.initialize(board.getAffiliatedTo());
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        
        model.addAttribute("board", board);
        
        if (board.isGroup()) {
            Hibernate.initialize(((Group) board).getAffiliates());
            return "/pages/groupInformation";
        } else {
            if (edit) {
                model.addAttribute("countries", countryDAO.list());
                model.addAttribute("counties", countyDAO.list());
                return "/pages/editTower";
            } else {
                return "/pages/towerInformation";                
            }
        }
        
    }
    
    @RequestMapping(value = "/information", method = RequestMethod.POST)
    public String newTower(@PathVariable final String boardId,
                           @ModelAttribute("board") final Tower tower,
                           final BindingResult bindingResult) {
        
        Board originalBoard = boardDAO.find(boardId);
        if (!originalBoard.isGroup()) {
            Tower originalTower = (Tower) originalBoard;
            originalTower.setIdentifier(tower.getIdentifier());
            originalTower.setDedication(tower.getDedication());
            originalTower.setArea(tower.getArea());
            originalTower.setTown(tower.getTown());
            originalTower.setCounty(tower.getCounty());
            originalTower.setNumBells(tower.getNumBells());
            originalTower.setTenorWeightCwt(tower.getTenorWeightCwt());
            originalTower.setTenorWeightQtrs(tower.getTenorWeightQtrs());
            originalTower.setTenorWeightLbs(tower.getTenorWeightLbs());
            boardDAO.update(originalTower);
        }
        return "redirect:information"; 
    }
    
    
}
