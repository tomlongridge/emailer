package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.Board;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO extends GenericDAO<Board, Long> {

    public Board find(final String identifier) {
        return (Board) currentSession()
                .createCriteria(Board.class)
                .add(Restrictions.eq("identifier", identifier))
                .uniqueResult();
    }


}
