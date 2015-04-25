package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.Board;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO extends GenericDAO<Board, Long> {

    public Board find(final String identifier) {
        return (Board) currentSession()
                .createCriteria(Board.class)
//                .setFetchMode("affiliatedTo", FetchMode.JOIN)
//                .setFetchMode("affiliates", FetchMode.JOIN)
                .add(Restrictions.eq("identifier", identifier))
                .uniqueResult();
    }


}
