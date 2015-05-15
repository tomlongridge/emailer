package org.bathbranchringing.emailer.tasks;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bathbranchringing.emailer.core.domain.Board;
import org.bathbranchringing.emailer.core.domain.Notice;
import org.bathbranchringing.emailer.core.domain.Notification;
import org.bathbranchringing.emailer.core.domain.NotificationStatus;
import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.NotificationDAO;
import org.bathbranchringing.emailer.core.services.Emailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@EnableScheduling
public class NotificationProcessor {
    
    static final Logger LOG = LoggerFactory.getLogger(NotificationProcessor.class);
    
    @Autowired
    private NotificationDAO notificationDAO;
    
    @Autowired
    private Emailer emailer;
    
    @Transactional(readOnly = false)
    @Scheduled(cron = "${notifications.cron}")
    public void sendNotifications() {
        
        LOG.info("Started processing notification queue");

        final List<Notification> queue = notificationDAO.getQueue();
        
        if (queue.size() == 0) {
            
            LOG.info("No notifications pending");
            
        } else {
        
            LOG.debug(String.format("%d notification(s) to process", queue.size()));
            
            // Remove obsolete notifications, where another newer notification exists for the same notice
            final Map<Long, Notification> noticeMap = new HashMap<Long, Notification>();
            for (Notification notification : queue) {
                final Long noticeId = notification.getNotice().getId();
                if (!noticeMap.containsKey(noticeId)) {
                    LOG.debug(String.format("Adding new notification for notice ID %d", noticeId));
                    noticeMap.put(noticeId, notification);
                } else {
                    Notification obsoleteNotification;
                    if (noticeMap.get(noticeId).getCreationDate().before(notification.getCreationDate())) {
                        obsoleteNotification = noticeMap.get(noticeId);
                        noticeMap.put(noticeId, notification);
                    } else {
                        obsoleteNotification = notification;
                    }
                    
                    LOG.debug(String.format("Obsolete notification ID %d found, updated and removed", obsoleteNotification.getId()));
                    notificationDAO.obsolete(obsoleteNotification);
                }            
            }
            
            LOG.debug(String.format("%d unique notification(s) to process", noticeMap.size()));
            
            for (Notification notification : noticeMap.values()) {
    
                final Notice notice = notification.getNotice();
                final Board board = notice.getBoard();
                final Collection<User> users = board.getUsers();
                
                LOG.info(String.format("Processing notification ID %d for board %s (ID: %d) to %d user(s)",
                        notification.getId(), board.getDisplayName(), board.getId(), users.size()));
                
                for (User user : users) {
                    emailer.send(user.getEmailAddress(),
                                 user.getDisplayName(),
                                 notice.getHeading(),
                                 notice.getContent());
                }
                
                notification.setClosedDate(new Date());
                notification.setStatus(NotificationStatus.SENT);
                notificationDAO.update(notification);
                
            }
            
        }
        
        LOG.info("Completed notification queue");
        
    }

}
