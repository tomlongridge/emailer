package org.bathbranchringing.emailer.core.repo;

import java.util.Date;
import java.util.List;

import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Notification;
import org.bathbranchringing.emailer.core.domain.NotificationStatus;
import org.bathbranchringing.emailer.core.domain.NotificationType;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDAO extends GenericDAO<Notification, Long> {
    
    public Long add(final Notice notice, final NotificationType type) {
        
        Notification notification = new Notification();
        notification.setNotice(notice);
        notification.setCreationDate(new Date());
        notification.setCreatedBy(notice.getLastModifiedBy());
        notification.setStatus(NotificationStatus.NEW);
        notification.setType(type);
        return super.add(notification);

    }
    
    public void obsolete(final Notification notification) {
        notification.setStatus(NotificationStatus.OBSOLETED);
        notification.setClosedDate(new Date());
        super.update(notification);
    }
    
    @SuppressWarnings("unchecked")
    public List<Notification> getQueue() {
        
        return currentSession()
                   .createCriteria(Notification.class)
                   .add(Restrictions.isNull("closedDate"))
                   .list();
        
    }
}
