package org.bathbranchringing.emailer.web.controllers;

import org.bathbranchringing.emailer.core.repo.CountryDAO;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.core.repo.GroupDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/" + TowerListController.URL_TOWERS)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TowerListController extends BaseController {
    
    public static final String URL_TOWERS = "towers";
    public static final String URL_SEARCH = "search";
    public static final String URL_BY_LOCATION = "locations";
    public static final String URL_BY_GROUP = "groups";

    private static final String PAGE_TOWER_BROWSE = "/pages/towerBrowse";
    private static final String PAGE_TOWER_SEARCH = "/pages/towerSearch";

    @Autowired
    private CountryDAO countryDAO;
    
    @Autowired
    private CountyDAO countyDAO;
    
    @Autowired
    private GroupDAO groupDAO;
	
	@Autowired
	private TowerDAO towerDAO;

	@RequestMapping({"/", ""})
	public String init() {
	    return PAGE_TOWER_SEARCH;
	}

	@RequestMapping("/" + URL_SEARCH)
	public String searchPage(@RequestParam(value = "q", required = false) final String query,
						     final ModelMap model) {
	    
	    if (!StringUtils.isEmpty(query)) {
	        model.addAttribute(URL_TOWERS, towerDAO.search(query));
	    }
	    return PAGE_TOWER_SEARCH;

	}
	
	@RequestMapping("/" + URL_BY_LOCATION)
	public String browseByLocationPage(final ModelMap model) {
        model.addAttribute("countries", countryDAO.list());
        return PAGE_TOWER_BROWSE;
	}
    
    @RequestMapping("/" + URL_BY_LOCATION + "/{country}")
    public String browseByCountry(@PathVariable final String country,
                                  final ModelMap model) {

    	model.addAttribute("selectedCountry", country);
        model.addAttribute("countries", countryDAO.list());
        model.addAttribute("counties", countyDAO.find(country));
        return PAGE_TOWER_BROWSE;
    }
    
    @RequestMapping("/" + URL_BY_LOCATION + "/{country}/{county}")
    public String browseByCountry(@PathVariable final String country,
                                  @PathVariable final String county,     
                                  final ModelMap model) {

    	model.addAttribute("selectedCountry", country);
    	model.addAttribute("selectedCounty", county);
        model.addAttribute("countries", countryDAO.list());
        model.addAttribute("counties", countyDAO.find(country));
        model.addAttribute("towers", towerDAO.find(county, country));
        return PAGE_TOWER_BROWSE;
    }
	
	@RequestMapping("/" + URL_BY_GROUP)
	public String browseByGroupPage(final ModelMap model) {
        model.addAttribute("groups", groupDAO.list());
        return PAGE_TOWER_BROWSE;
	}
	
	@RequestMapping("/" + URL_BY_GROUP + "/{groups:.*}")
	public String browseByGroupPage(@PathVariable final String groups,
									final ModelMap model) {
        model.addAttribute("groups", groupDAO.list(groups));
        return PAGE_TOWER_BROWSE;
	}
    
}
