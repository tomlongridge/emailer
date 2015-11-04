package org.bathbranchringing.emailer.core.repo;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.Tower;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TowerDAO extends GenericDAO<Tower, Long> {
    
    @SuppressWarnings("unchecked")
    public List<Tower> find(final String county, final String country) {
        final Criteria criteria = currentSession()
                .createCriteria(Tower.class)
                .createAlias("county", "county")
                .createAlias("county.country", "country");
        if (county != null) {
            criteria.add(Restrictions.eq("county.name", county).ignoreCase());
        }
        if (country != null) {
            criteria.add(Restrictions.eq("country.name", country).ignoreCase());
        }
        return criteria.list();
    }
    
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
    
    @SuppressWarnings("unchecked")
    public List<Tower> listAffiliates(final String groupId) {
    	return currentSession().createQuery("from Tower").list();
    }

}
