package org.bathbranchringing.emailer.core.repo;

import org.bathbranchringing.emailer.core.domain.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.find(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    String.format("A user with email address \"%s\" not found.", username));
        } else {
            Hibernate.initialize(user.getAuthorities());
            return user;
        }
    }

}
