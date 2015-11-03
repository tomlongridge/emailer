package org.bathbranchringing.emailer.web.services;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Group;
import org.bathbranchringing.emailer.core.repo.GroupDAO;
import org.bathbranchringing.emailer.core.repo.TowerDAO;
import org.bathbranchringing.emailer.web.models.TowerBrowseByGroupModel;
import org.bathbranchringing.emailer.web.models.TowerBrowseGroupList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TowerBrowseByGroupViewService {
    
    private static final Logger LOG = LoggerFactory.getLogger(TowerBrowseByGroupViewService.class);
    
    @Autowired
    private GroupDAO groupDAO; 
    
    @Autowired
    private TowerDAO towerDAO;

    public TowerBrowseByGroupModel populateModel(final List<String> groups) {
        
        LOG.info("-> Populating model");
        
        final TowerBrowseByGroupModel model = new TowerBrowseByGroupModel();
        
        // Get top-level groups (those not affiliated to another group)
        LOG.info("Adding top-level groups");
        final TowerBrowseGroupList topLevelGroups = new TowerBrowseGroupList();
        for (Group group : groupDAO.list()) {
            if (group.getAffiliatedTo().isEmpty()) {
                LOG.info("Adding top-level group {}", group.getIdentifier());
                topLevelGroups.getGroups().put(group.getIdentifier(), group.getDisplayName());
            }
        }
        LOG.info("Added {} top-level groups", topLevelGroups.getGroups().size());
        model.getGroupLists().add(topLevelGroups);
        
        LOG.info("Adding sub-groups");
        TowerBrowseGroupList lastGroupList = topLevelGroups;
        for (String groupId : groups) {
            Group group = groupDAO.find(groupId);
            if (group != null) {
                LOG.info("Setting {} as a selected group", groupId);
                lastGroupList.setSelectedGroup(groupId);
                
                lastGroupList = new TowerBrowseGroupList();
                for (Board affiliate : group.getAffiliates()) {
                    if (affiliate.isGroup()) {
                        LOG.info("Adding group {}", affiliate.getIdentifier());
                        lastGroupList.getGroups().put(affiliate.getIdentifier(), affiliate.getDisplayName());
                    }
                }
                LOG.info("Added {} sub-groups", lastGroupList.getGroups().size());
            } else {
                LOG.info("Unable to find group {}, ignoring from model", groupId);
            }
        }
        
        LOG.info("<- Populating model");
        return model;
    }
    
}
