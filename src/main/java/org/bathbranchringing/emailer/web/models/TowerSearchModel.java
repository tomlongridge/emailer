package org.bathbranchringing.emailer.web.models;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Tower;

public class TowerSearchModel {

    private String query;
    
    private List<Tower> towers;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }
    
}
