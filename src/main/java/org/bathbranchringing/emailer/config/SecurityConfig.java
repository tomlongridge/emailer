package org.bathbranchringing.emailer.config;

import org.bathbranchringing.emailer.web.URLConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDAO;
    
    @Autowired
    private AuthenticationRequestFilter authRequestFilter;
    
    @Autowired
    public void configure(final AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDAO).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
        .authorizeRequests()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/js/**").permitAll()
            .antMatchers("/fonts/**").permitAll()
        .and()
            .addFilterAfter(authRequestFilter, FilterSecurityInterceptor.class)
        .formLogin()
            .loginPage("/" + URLConstants.LOGIN)
            .permitAll()
        .and()
            .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(true)
                .logoutUrl("/" + URLConstants.LOGOUT)
                .logoutSuccessUrl("/" + URLConstants.LOGIN + "?logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/" + URLConstants.LOGOUT))
                .permitAll();
        
    }

}
