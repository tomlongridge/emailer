package org.bathbranchringing.emailer.core.repo;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Group;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDAO extends GenericDAO<Group, Long> {

    public Group find(final String identifier) {
        return (Group) currentSession()
                .createCriteria(Group.class)
                .add(Restrictions.eq("identifier", identifier))
                .uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Group> search(final String searchString) {
        
        return currentSession()
                .createCriteria(Group.class)
                .add(
                		
                        Restrictions.or(
                                Restrictions.ilike("name", searchString, MatchMode.ANYWHERE)
                        ))
                .list();
    }

}