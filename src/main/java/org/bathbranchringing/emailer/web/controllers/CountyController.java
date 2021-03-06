package org.bathbranchringing.emailer.web.controllers;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.County;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.web.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/" + URLConstants.COUNTRIES)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class CountyController extends BaseController {
    
    @Autowired
    private CountyDAO countyDAO;
    
	@RequestMapping(value = "/{country}/" + URLConstants.COUNTIES, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<County> getCounties(@PathVariable final String country) {
	    return countyDAO.find(country);
	}
	
}
