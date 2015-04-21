package org.bathbranchringing.emailer.core.repo;

import java.util.Date;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO extends GenericDAO<Notice, Long> {

	@SuppressWarnings("unchecked")
    public List<Notice> getBoardNotices(final Board board) {
        return currentSession()
        		.createCriteria(Notice.class)
        		.add(Restrictions.eq("board.id", board.getId()))
        		.add(Restrictions.isNull("startDate"))
        		.list();
    }

    @SuppressWarnings("unchecked")
    public List<Notice> getBoardNotices(final Board board, final Date dateFrom, final Date dateTo) {
        return currentSession()
                .createCriteria(Notice.class)
                .add(Restrictions.eq("board.id", board.getId()))
                .add(Restrictions.gt("creationDate", dateFrom))
                .add(Restrictions.lt("creationDate", dateTo))
                .add(Restrictions.isNull("startDate"))
                .list();
    }

}
