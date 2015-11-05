package org.bathbranchringing.emailer.web.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.bathbranchringing.emailer.web.URLConstants;
import org.bathbranchringing.emailer.web.services.TowerBrowseByGroupViewService;
import org.bathbranchringing.emailer.web.services.TowerBrowseByLocationViewService;
import org.bathbranchringing.emailer.web.services.TowerSearchViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerMapping;

@Controller
@RequestMapping("/" + URLConstants.TOWERS)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TowerListController extends BaseController {
    
    private static final String PAGE_TOWER_BROWSE_BY_LOCATION = "/pages/towerBrowseByLocation";
    private static final String PAGE_TOWER_BROWSE_BY_GROUP = "/pages/towerBrowseByGroup";
    private static final String PAGE_TOWER_SEARCH = "/pages/towerSearch";

    @Autowired
    private TowerSearchViewService towerSearchService;

    @Autowired
    private TowerBrowseByLocationViewService towerBrowseByLocationViewService;
    
    @Autowired
    private TowerBrowseByGroupViewService towerBrowseByGroupViewService;
    
	@RequestMapping({"/", ""})
	public String init(final ModelMap model) {
	    model.addAttribute(towerSearchService.populateModel(null));
	    return PAGE_TOWER_SEARCH;
	}

	@RequestMapping("/" + URLConstants.SEARCH_BOARDS)
	public String searchPage(@RequestParam(value = "q", required = false) final String query,
						     final ModelMap model) {
	    model.addAttribute(towerSearchService.populateModel(query));
	    return PAGE_TOWER_SEARCH;
	}
	
	@RequestMapping("/" + URLConstants.SEARCH_BY_LOCATIONS)
	public String browseByLocationPage(final ModelMap model) {
        model.addAttribute(towerBrowseByLocationViewService.populateModel(null, null));
        return PAGE_TOWER_BROWSE_BY_LOCATION;
	}
    
    @RequestMapping("/" + URLConstants.SEARCH_BY_LOCATIONS + "/{country}")
    public String browseByCountry(@PathVariable final String country,
                                  final ModelMap model) {
        model.addAttribute(towerBrowseByLocationViewService.populateModel(country, null));
        return PAGE_TOWER_BROWSE_BY_LOCATION;
    }
    
    @RequestMapping("/" + URLConstants.SEARCH_BY_LOCATIONS + "/{country}/{county}")
    public String browseByCountry(@PathVariable final String country,
                                  @PathVariable final String county,     
                                  final ModelMap model) {
        model.addAttribute(towerBrowseByLocationViewService.populateModel(country, county));
        return PAGE_TOWER_BROWSE_BY_LOCATION;
    }
	
	@RequestMapping("/" + URLConstants.SEARCH_BY_GROUPS)
	public String browseByGroupPage(final ModelMap model) {
        model.addAttribute(towerBrowseByGroupViewService.populateModel(null));
        return PAGE_TOWER_BROWSE_BY_GROUP;
	}
	
	@RequestMapping("/" + URLConstants.SEARCH_BY_GROUPS + "/**")
	public String browseByGroupPageGroup(final ModelMap model,
	                                     final HttpServletRequest request) {
	    String requestPath = (String) request.getAttribute(
	            HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    requestPath = requestPath.substring(URLConstants.SEARCH_BY_GROUPS.length() + requestPath.indexOf(URLConstants.SEARCH_BY_GROUPS) + 1);
        model.addAttribute(towerBrowseByGroupViewService.populateModel(Arrays.asList(requestPath.split("/"))));
        return PAGE_TOWER_BROWSE_BY_GROUP;
	}
    
}
