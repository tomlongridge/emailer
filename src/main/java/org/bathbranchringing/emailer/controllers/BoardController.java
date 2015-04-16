package org.bathbranchringing.emailer.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.repo.GroupDAO;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/towers/{boardId}", "/groups/{boardId}"})
public class BoardController {
    
    @Autowired
    private GroupDAO groupDAO;
    
    @Autowired
    private TowerDAO towerDAO;
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping({"", "/", "/notices"})
	public String noticesPage(@PathVariable final String boardId,
                              final ModelMap model) {
		
		String page = "redirect:/home";
		Board board = towerDAO.find(boardId);
		if (board != null) {
		    model.addAttribute("tower", board);
		    page = "/pages/tower";
		} else {
		    board = groupDAO.find(boardId);
		    if (board != null) {
		        model.addAttribute("group", board);
		        page = "/pages/group";
		    }
		}
		
		if (board != null) {
    		final List<Notice> notices = noticeDAO.getBoardNotices(board);
    		model.addAttribute("notices", notices);
		}
		
		return page;
	}
    
    @RequestMapping("/notices/{year}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              final ModelMap model) {
        
        String page = "redirect:/home";
        Board board = towerDAO.find(boardId);
        if (board != null) {
            model.addAttribute("tower", board);
            page = "/pages/tower";
        } else {
            board = groupDAO.find(boardId);
            if (board != null) {
                model.addAttribute("group", board);
                page = "/pages/group";
            }
        }
        
        if (board != null) {
            final Calendar dateFrom = GregorianCalendar.getInstance();
            dateFrom.set(year, 1, 1, 0, 0, 0);
            final Calendar dateTo = GregorianCalendar.getInstance();
            dateTo.set(year + 1, 1, 1, 0, 0, 0);
            final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
            model.addAttribute("notices", notice);
        }
        
        return page;
    }
    
    @RequestMapping("/notices/{year}/{month}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              final ModelMap model) {
        
        String page = "redirect:/home";
        Board board = towerDAO.find(boardId);
        if (board != null) {
            model.addAttribute("tower", board);
            page = "/pages/tower";
        } else {
            board = groupDAO.find(boardId);
            if (board != null) {
                model.addAttribute("group", board);
                page = "/pages/group";
            }
        }
        
        if (board != null) {
            final Calendar dateFrom = GregorianCalendar.getInstance();
            dateFrom.set(year, month - 1, 1, 0, 0, 0);
            final Calendar dateTo = GregorianCalendar.getInstance();
            dateTo.set(year, month, 1, 0, 0, 0);
            final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
            model.addAttribute("notices", notice);
        }
        
        return page;
    }
    
    @RequestMapping("/notices/{year}/{month}/{day}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              @PathVariable final int day,
                              final ModelMap model) {
        
        String page = "redirect:/home";
        Board board = towerDAO.find(boardId);
        if (board != null) {
            model.addAttribute("tower", board);
            page = "/pages/tower";
        } else {
            board = groupDAO.find(boardId);
            if (board != null) {
                model.addAttribute("group", board);
                page = "/pages/group";
            }
        }
        
        if (board != null) {
            final Calendar dateFrom = GregorianCalendar.getInstance();
            dateFrom.set(year, month - 1, day, 0, 0, 0);
            final Calendar dateTo = GregorianCalendar.getInstance();
            dateTo.set(year, month - 1, day + 1, 0, 0, 0);
            final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
            model.addAttribute("notices", notice);
        }
        
        return page;
    }
	
	@RequestMapping(value = "/notices/{year}/{month}/{day}/{noticeId}")
	public String noticePage(@PathVariable final String boardId,
                             @PathVariable final long year,
                             @PathVariable final long month,
                             @PathVariable final long day,
                             @PathVariable final long noticeId,
                             @RequestParam(required = false, defaultValue = "false") final boolean edit,
	                         final ModelMap model) {
        
        Board board = towerDAO.find(boardId);
        if (board != null) {
            model.addAttribute("tower", board);
        } else {
            board = groupDAO.find(boardId);
            if (board != null) {
                model.addAttribute("group", board);
            }
        }
        
        String page = "redirect:/home";
        if (board != null) {
    		final Notice notice = noticeDAO.find(noticeId);
    		if ((notice != null) && (notice.getBoard().getId() == board.getId())) {
    		    if (edit) {
    		        page = "/pages/editNotice";
    		    } else {
    		        page = "/pages/viewNotice";
    		    }
    		    model.addAttribute("notice", notice);
    		}
        }
		
		return page;
	}
	
}
