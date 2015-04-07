package org.bathbranchringing.emailer.controllers;

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
public class BoardController {
	
	@Autowired
	private TowerDAO towerDAO;
	
	@Autowired
	private NoticeDAO noticeDAO;
	
	@RequestMapping({"/towers/{id}", "/towers/{towerId}/notices"})
	public String towerPage(@PathVariable final String towerId, final ModelMap model) {
		
		final Tower tower = towerDAO.find(towerId);
		if (tower == null) {
			return "redirect:/pages/home";
		}
		model.addAttribute("tower", tower);
		
		final List<Notice> notices = noticeDAO.getTowerNotices(tower);
		model.addAttribute("notices", notices);
		
		return "/pages/tower";
	}
	
	@RequestMapping("/towers/{towerId}/notices/{noticeId}")
	public String noticePage(@PathVariable final String towerId, @PathVariable final long noticeId, final ModelMap model) {
		
		final Tower tower = towerDAO.find(towerId);
		if (tower == null) {
			return "redirect:/pages/home";
		}
		model.addAttribute("tower", tower);
		
		final Notice notice = noticeDAO.find(noticeId);
		if ((notice == null) || (notice.getTower().getId() != tower.getId())) {
			return "redirect:/pages/home";
		}
		model.addAttribute("notice", notice);
		
		return "/pages/notice";
	}
	
}
