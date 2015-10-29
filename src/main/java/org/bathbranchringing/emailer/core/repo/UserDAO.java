package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends GenericDAO<User, Long> {

    public User find(final String emailAddress) {

        return (User) currentSession()
                        .createCriteria(User.class)
                        .add(Restrictions.eq("emailAddress", emailAddress))
                        .uniqueResult();
    
    }
}
