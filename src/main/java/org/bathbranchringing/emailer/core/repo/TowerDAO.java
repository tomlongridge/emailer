package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.Tower;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TowerDAO extends GenericDAO<Tower, Long> {

	public Tower find(final String doveId) {
		return (Tower) currentSession()
				.createCriteria(Tower.class)
				.add(Restrictions.eq("doveId", doveId))
				.uniqueResult();
	}

}
