package org.bathbranchringing.emailer.core.repo;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Tower;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TowerDAO extends GenericDAO<Tower, Long> {
    
    @SuppressWarnings("unchecked")
    public List<Tower> search(final String searchString) {
        
        return currentSession()
                .createCriteria(Tower.class)
                .createAlias("county", "county")
                .createAlias("county.country", "country")
                .add(
                        Restrictions.or(
                                Restrictions.ilike("dedication", searchString, MatchMode.ANYWHERE),
                                Restrictions.ilike("area", searchString, MatchMode.ANYWHERE),
                                Restrictions.ilike("town", searchString, MatchMode.ANYWHERE),
                                Restrictions.ilike("county.name", searchString, MatchMode.ANYWHERE),
                                Restrictions.ilike("country.name", searchString, MatchMode.ANYWHERE)
                        ))
                .list();
    }

}
