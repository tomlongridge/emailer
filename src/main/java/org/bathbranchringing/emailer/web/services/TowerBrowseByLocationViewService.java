package org.bathbranchringing.emailer.web.services;

import org.bathbranchringing.emailer.core.repo.CountryDAO;
import org.bathbranchringing.emailer.core.repo.CountyDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.bathbranchringing.emailer.web.models.TowerBrowseByLocationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TowerBrowseByLocationViewService {

    @Autowired
    private CountryDAO countryDAO;
    
    @Autowired
    private CountyDAO countyDAO;
    
    @Autowired
    private TowerDAO towerDAO;
    
    public TowerBrowseByLocationModel populateModel(final String countryName,
                                                    final String countyName) {
        
        TowerBrowseByLocationModel model = new TowerBrowseByLocationModel();
        
        model.setCountries(countryDAO.list());
        if (!StringUtils.isEmpty(countryName)) {
            model.setSelectedCountryName(countryName);
            model.setCounties(countyDAO.find(countryName));
            if (!StringUtils.isEmpty(countyName)) {
                model.setSelectedCountyName(countyName);
                model.setTowers(towerDAO.find(countyName, countryName));
            }
        }
        
        return model;
    }
    
}
