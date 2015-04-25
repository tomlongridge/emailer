package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.User;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAO extends GenericDAO<User, Long> implements UserDetailsService {

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = (User) currentSession()
                        .createCriteria(User.class)
                        .add(Restrictions.eq("emailAddress", username))
                        .uniqueResult();
        
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("A user with email address \"%s\" not found.", username));
        } else {
            Hibernate.initialize(user.getAuthorities());
            return user;
        }
    }

}
