package org.bathbranchringing.emailer.core.repo;

import java.util.List;

import org.bathbranchringing.emailer.core.domain.County;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CountyDAO extends GenericDAO<County, Long> {

    @SuppressWarnings("unchecked")
    public List<County> find(final String country) {
        return currentSession()
                .createCriteria(County.class)
                .createAlias("country", "country")
                .add(Restrictions.eq("country.name", country))
                .list();
    }

}
