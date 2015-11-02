package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.core.repo.CountryDAO;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/" + TowerListController.URL_TOWERS)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TowerListController extends BaseController {
    
    public static final String URL_TOWERS = "towers";

    private static final String PAGE_TOWER_BROWSE = "/pages/towerBrowse";
    private static final String PAGE_TOWER_SEARCH = "/pages/towerSearch";

    @Autowired
    private CountryDAO countryDAO;
    
    @Autowired
    private CountyDAO countyDAO;
	
	@Autowired
	private TowerDAO towerDAO;
	
	@RequestMapping({"/", ""})
	public String towersPage(@RequestParam(required = false) final String search,
						     final ModelMap model) {
	    
	    if (search != null) {
	        model.addAttribute(URL_TOWERS, towerDAO.search(search));
	        return PAGE_TOWER_SEARCH;
	    } else {
	        model.addAttribute("countries", countryDAO.list());
	        return PAGE_TOWER_BROWSE;
	    }

	}
    
    @RequestMapping("/{country}")
    public String browseByCountry(@PathVariable final String country,
                                  final ModelMap model) {

        model.addAttribute("counties", countyDAO.find(country));
        return PAGE_TOWER_BROWSE;
    }
    
    @RequestMapping("/{country}/{county}")
    public String browseByCountry(@PathVariable final String country,
                                  @PathVariable final String county,     
                                  final ModelMap model) {

        model.addAttribute("towers", towerDAO.find(county, country));
        return PAGE_TOWER_BROWSE;
    }
    
}
