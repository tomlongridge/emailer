package org.bathbranchringing.emailer.web.models;

import java.util.ArrayList;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Tower;

public class TowerBrowseByGroupModel {
    
    private List<TowerBrowseGroupList> groupLists;
    
    private List<Tower> towers;
    
    public TowerBrowseByGroupModel() {
        groupLists = new ArrayList<>();
        towers = new ArrayList<>();
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void setTowers(List<Tower> towers) {
        this.towers = towers;
    }

    public List<TowerBrowseGroupList> getGroupLists() {
        return groupLists;
    }

    public void setGroupLists(List<TowerBrowseGroupList> groupLists) {
        this.groupLists = groupLists;
    }
}
