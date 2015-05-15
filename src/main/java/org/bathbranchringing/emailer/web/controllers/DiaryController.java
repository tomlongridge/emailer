package org.bathbranchringing.emailer.web.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Event;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.bathbranchringing.emailer.core.repo.EventDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({"/towers/{boardId}/diary", "/groups/{boardId}/diary"})
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class DiaryController {
    
    @Value("${diary.numMonths}")
    private String numMonthsInDiaryView;
    
    @Autowired
    private BoardDAO boardDAO;
    
	@Autowired
	private EventDAO eventDAO;
	
	@RequestMapping({"", "/"})
	public String diaryPage(@PathVariable final String boardId,
                            final ModelMap model) {
		
		final Board board = boardDAO.find(boardId);
		if (board == null) {
		    return "redirect:/home";
		}
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
		model.addAttribute("board", board);
		
		final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(Calendar.DAY_OF_MONTH, 1);
        dateFrom.set(Calendar.HOUR_OF_DAY, 0);
        dateFrom.set(Calendar.MINUTE, 0);
        dateFrom.set(Calendar.SECOND, 0);
        dateFrom.set(Calendar.MILLISECOND, 0);
        final Calendar dateTo = (Calendar) dateFrom.clone();
        dateTo.add(Calendar.MONTH, Integer.valueOf(numMonthsInDiaryView));
        
        final SortedMap<Long, SortedMap<Long, List<Event>>> diary = new TreeMap<Long, SortedMap<Long, List<Event>>>();
        for (Event event : eventDAO.getDiary(board, dateFrom.getTime(), dateTo.getTime())) {
            
            final Long yearKey = Long.valueOf(dateFrom.get(Calendar.YEAR));
            if (!diary.containsKey(yearKey)) {
                diary.put(yearKey, new TreeMap<Long, List<Event>>());
            }
            
            final Long monthKey = Long.valueOf(dateFrom.get(Calendar.MONTH));
            if (!diary.get(yearKey).containsKey(monthKey)) {
                diary.get(yearKey).put(monthKey, new ArrayList<Event>());
            }
            
            diary.get(yearKey).get(monthKey).add(event);
        }
        
		model.addAttribute("diary", diary);
		
		return "/pages/diary";
	}
    
    @RequestMapping("/{year}")
    public String eventsPage(@PathVariable final String boardId,
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
        final List<Event> diary = eventDAO.getDiary(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("diary", diary);
        
        model.addAttribute("title", String.format("%1$tY", dateFrom));

        return "/pages/diary";
    }
    
    @RequestMapping("/{year}/{month}")
    public String eventsPage(@PathVariable final String boardId,
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
        final List<Event> diary = eventDAO.getDiary(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("diary", diary);

        model.addAttribute("title", String.format("%1$tB %1$tY", dateFrom));
        
        return "/pages/diary";
    }
    
    @RequestMapping("/{year}/{month}/{day}")
    public String eventsPage(@PathVariable final String boardId,
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
        final List<Event> diary = eventDAO.getDiary(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("diary", diary);
        
        model.addAttribute("title", String.format("%1$te %1$tB %1$tY", dateFrom));

        return "/pages/diary";
    }
	
	@RequestMapping(value = "/{year}/{month}/{day}/{noticeId}")
	public String eventPage(@PathVariable final String boardId,
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
        
		final Event event = eventDAO.find(noticeId);
		if ((event == null) || (event.getBoard().getId() != board.getId())) {
            return "redirect:/home";
		}
		
		model.addAttribute("notice", event);
	    if (edit) {
	        return "/pages/editEvent";
	    } else {
	        return "/pages/viewEvent";
	    }
		
	}
    
    @RequestMapping("/new")
    public String newEvent(@PathVariable final String boardId,
                           final ModelMap model) {
        
        final Board board = boardDAO.find(boardId);
        if (board == null) {
            return "redirect:/home";
        }
        Hibernate.initialize(board.getSubscribers());
        Hibernate.initialize(board.getMembers());
        model.addAttribute("board", board);
        
        Event event = new Event();
        event.setBoard(board);
        model.addAttribute("notice", event);
        
        return "/pages/editEvent";
    }
	
}
