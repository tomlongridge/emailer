package org.bathbranchringing.emailer.web.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.validation.Valid;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Group;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Tower;
import org.bathbranchringing.emailer.core.repo.BoardDAO;
import org.bathbranchringing.emailer.core.repo.CountryDAO;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.core.repo.NoticeDAO;
import org.bathbranchringing.emailer.web.URLConstants;
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
@RequestMapping(
        {
            "/" + URLConstants.SINGLE_TOWER + "/{boardId}",
            "/" + URLConstants.SINGLE_GROUP + "/{boardId}"
        }
)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class BoardController extends BaseController {

	private static final String PAGE_BOARD = "/pages/board";
	private static final String PAGE_TOWER_INFORMATION = "/pages/towerInformation";
	private static final String PAGE_GROUP_INFORMATION = "/pages/groupInformation";
	private static final String PAGE_EDIT_TOWER = "/pages/editTower";
	private static final String PAGE_VIEW_NOTICE = "/pages/viewNotice";
	private static final String PAGE_EDIT_NOTICE = "/pages/editNotice";

	@Autowired
    private BoardDAO boardDAO;
    
	@Autowired
	private NoticeDAO noticeDAO;
    
    @Autowired
    private CountyDAO countyDAO;
    
    @Autowired
    private CountryDAO countryDAO;
	
	@RequestMapping(value = {"", "/", "/" + URLConstants.NOTICES}, method = RequestMethod.GET)
	public String noticesPage(@PathVariable final String boardId,
                              final ModelMap model) {
		
		final Board board = initialise(model, boardId);
		if (board == null) {
		    return redirect(URLConstants.HOME);
		}
		
		final List<Notice> notices = noticeDAO.getBoardNotices(board);
		model.addAttribute("notices", notices);
		return PAGE_BOARD;
	}
    
    @RequestMapping("/" + URLConstants.NOTICES + "/{year}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, Calendar.JANUARY, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year + 1, Calendar.JANUARY, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);

        return PAGE_BOARD;
    }
    
    @RequestMapping("/" + URLConstants.NOTICES + "/{year}/{month}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, 1, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month, 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);
        
        return PAGE_BOARD;
    }
    
    @RequestMapping("/" + URLConstants.NOTICES + "/{year}/{month}/{day}")
    public String noticesPage(@PathVariable final String boardId,
                              @PathVariable final int year,
                              @PathVariable final int month,
                              @PathVariable final int day,
                              final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
        final Calendar dateFrom = GregorianCalendar.getInstance();
        dateFrom.set(year, month - 1, day, 0, 0, 0);
        final Calendar dateTo = GregorianCalendar.getInstance();
        dateTo.set(year, month - 1, day + 1, 0, 0, 0);
        final List<Notice> notice = noticeDAO.getBoardNotices(board, dateFrom.getTime(), dateTo.getTime());
        model.addAttribute("notices", notice);

        return PAGE_BOARD;
    }
	
	@RequestMapping(value = "/" + URLConstants.NOTICES + "/{year}/{month}/{day}/{noticeId}")
	public String noticePage(@PathVariable final String boardId,
                             @PathVariable final long year,
                             @PathVariable final long month,
                             @PathVariable final long day,
                             @PathVariable final long noticeId,
                             @RequestParam(required = false, defaultValue = "false") final boolean edit,
	                         final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
		final Notice notice = noticeDAO.find(noticeId);
		if ((notice == null) || (notice.getBoard().getId() != board.getId())) {
            return redirect(URLConstants.HOME);
		}
		
		model.addAttribute("notice", notice);
	    if (edit) {
	        return PAGE_EDIT_NOTICE;
	    } else {
	        return PAGE_VIEW_NOTICE;
	    }
		
	}
    
    @RequestMapping("/" + URLConstants.NOTICES + "/" + URLConstants.NEW_ENTITY)
    public String newNotice(@PathVariable final String boardId,
                              final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }

        Notice notice = new Notice();
        notice.setBoard(board);
        model.addAttribute("notice", notice);
        
        return PAGE_EDIT_NOTICE;
    }
    
    @RequestMapping("/" + URLConstants.BOARD_INFORMATION)
    public String infoPage(@PathVariable final String boardId,
                           @RequestParam(required = false, defaultValue = "false") final boolean edit,
                           final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
        Hibernate.initialize(board.getAffiliatedTo());
        
        if (board.isGroup()) {
            Hibernate.initialize(((Group) board).getAffiliates());
            return PAGE_GROUP_INFORMATION;
        } else {
            if (edit) {
                model.addAttribute("editableTower", board);
                model.addAttribute("countries", countryDAO.list());
                model.addAttribute("counties", countyDAO.list());
                return PAGE_EDIT_TOWER;
            } else {
                return PAGE_TOWER_INFORMATION;                
            }
        }
        
    }
    
    @RequestMapping(value = "/" + URLConstants.BOARD_INFORMATION, method = RequestMethod.POST)
    public String editTower(@PathVariable final String boardId,
                            @ModelAttribute("editableTower") @Valid final Tower tower,
                            final BindingResult bindingResult,
                            final ModelMap model) {

        final Board board = initialise(model, boardId);
        if (board == null) {
            return redirect(URLConstants.HOME);
        }
        
        Hibernate.initialize(board.getAffiliatedTo());

        if (board.isAdmin(getUser())) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("editableTower", tower);
                model.addAttribute("countries", countryDAO.list());
                model.addAttribute("counties", countyDAO.list());
                return PAGE_EDIT_TOWER; 
            } else if (!board.isGroup()) {
                
                Tower originalTower = (Tower) board;
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
                return redirect(URLConstants.BOARD_INFORMATION); 
            }
        }
        return redirect(URLConstants.HOME); 
    }
    
    
}
