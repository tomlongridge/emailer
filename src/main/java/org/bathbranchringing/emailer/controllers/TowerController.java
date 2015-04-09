package org.bathbranchringing.emailer.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Tower;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/towers/{towerId}")
public class TowerController {
	
	@Autowired
	private TowerDAO towerDAO;
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping({"", "/", "/notices"})
	public String noticesPage(@PathVariable final String towerId,
						     final ModelMap model) {
		
		final Tower tower = towerDAO.find(towerId);
		if (tower == null) {
			return "redirect:/home";
		}
		model.addAttribute("tower", tower);
		
		final List<Notice> notices = noticeDAO.getTowerNotices(tower);
		model.addAttribute("notices", notices);
		return "/pages/tower";
	}
    
    @RequestMapping("/notices/{year}")
    public String noticesPage(@PathVariable final String towerId,
                              @PathVariable final int year,
                              final ModelMap model) {
        
        final Tower tower = towerDAO.find(towerId);
        if (tower == null) {
            return "redirect:/home";
        }
        model.addAttribute("tower", tower);
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, 1, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year + 1, 1, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getTowerNotices(tower, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);
        
        return "/pages/tower";
    }
    
    @RequestMapping("/notices/{year}/{month}")
    public String noticesPage(@PathVariable final String towerId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              final ModelMap model) {
        
        final Tower tower = towerDAO.find(towerId);
        if (tower == null) {
            return "redirect:/home";
        }
        model.addAttribute("tower", tower);
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getTowerNotices(tower, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);
        
        return "/pages/tower";
    }
    
    @RequestMapping("/notices/{year}/{month}/{day}")
    public String noticesPage(@PathVariable final String towerId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              @PathVariable final int day,
                              final ModelMap model) {
        
        final Tower tower = towerDAO.find(towerId);
        if (tower == null) {
            return "redirect:/home";
        }
        model.addAttribute("tower", tower);
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, day, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month - 1, day + 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getTowerNotices(tower, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);
        
        return "/pages/tower";
    }
	
	@RequestMapping("/notices/{year}/{month}/{day}/{noticeId}")
	public String noticePage(@PathVariable final String towerId,
                             @PathVariable final long year,
                             @PathVariable final long month,
                             @PathVariable final long day,
                             @PathVariable final long noticeId,
	                         final ModelMap model) {
		
		final Tower tower = towerDAO.find(towerId);
		if (tower == null) {
			return "redirect:/home";
		}
		model.addAttribute("tower", tower);
		
		final Notice notice = noticeDAO.find(noticeId);
		if ((notice == null) || (notice.getTower().getId() != tower.getId())) {
			return "redirect:/home";
		}
		model.addAttribute("notice", notice);
		
		return "/pages/notice";
	}
	
}
