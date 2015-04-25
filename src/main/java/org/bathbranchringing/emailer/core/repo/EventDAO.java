package org.bathbranchringing.emailer.core.repo;

import java.util.Date;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Event;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class EventDAO extends GenericDAO<Event, Long> {

    @SuppressWarnings("unchecked")
    public List<Event> getDiary(final Board board, final Date dateFrom, final Date dateTo) {
        return currentSession()
                .createCriteria(Event.class)
                .add(Restrictions.eq("board.id", board.getId()))
                .add(Restrictions.or(
                        Restrictions.and(
                                Restrictions.gt("startDate", dateFrom),
                                Restrictions.lt("startDate", dateTo)
                        ),
                        Restrictions.and(
                                Restrictions.gt("endDate", dateFrom),
                                Restrictions.lt("endDate", dateTo)
                        )
                ))
                .addOrder(Order.asc("startDate"))
                .list();
    }

}
