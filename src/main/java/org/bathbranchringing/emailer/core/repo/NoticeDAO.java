package org.bathbranchringing.emailer.core.repo;

import java.util.Date;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Tower;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO extends GenericDAO<Notice, Long> {

	@SuppressWarnings("unchecked")
    public List<Notice> getTowerNotices(final Tower tower) {
        return currentSession()
        		.createCriteria(Notice.class)
        		.add(Restrictions.eq("tower.id", tower.getId()))
        		.list();
    }

    @SuppressWarnings("unchecked")
    public List<Notice> getTowerNotices(final Tower tower, final Date dateFrom, final Date dateTo) {
        return currentSession()
                .createCriteria(Notice.class)
                .add(Restrictions.eq("tower.id", tower.getId()))
                .add(Restrictions.gt("creationDate", dateFrom))
                .add(Restrictions.lt("creationDate", dateTo))
                .list();
    }

}
