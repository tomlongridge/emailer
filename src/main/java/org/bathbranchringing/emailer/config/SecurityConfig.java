package org.bathbranchringing.emailer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private UserDetailsService userDAO;
    
    @Autowired
    private UpdateSavedRequestFilter savedRequestFilter;
    
    @Autowired
    public void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDAO);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
        .authorizeRequests()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/fonts/**").permitAll()
        .and()
            .addFilterAfter(savedRequestFilter, FilterSecurityInterceptor.class)
        .formLogin()
            .loginPage("/login")
            .permitAll()
        .and()
            .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll();
        
    }

}
