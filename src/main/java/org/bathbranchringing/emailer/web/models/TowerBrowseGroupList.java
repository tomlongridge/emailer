package org.bathbranchringing.emailer.web.models;

import java.util.HashMap;
import java.util.Map;

public class TowerBrowseGroupList {

    private Map<String,String> groups;
    
    private String selectedGroup;
    
    public TowerBrowseGroupList() {
        groups = new HashMap<>();
    }
    
    public String getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(final String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Map<String,String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String,String> groups) {
        this.groups = groups;
    }
    
}