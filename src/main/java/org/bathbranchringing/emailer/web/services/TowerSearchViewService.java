package org.bathbranchringing.emailer.web.services;

import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.bathbranchringing.emailer.web.models.TowerSearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TowerSearchViewService {
    
    @Autowired
    private TowerDAO towerDAO;

    public TowerSearchModel populateModel(final String query) {
        TowerSearchModel model = new TowerSearchModel();
        if (!StringUtils.isEmpty(query)) {
        	model.setQuery(query);
            model.setTowers(towerDAO.search(query));
        }
        return model;
    }
    
}
