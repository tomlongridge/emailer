package org.bathbranchringing.emailer.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class UpdateSavedRequestFilter extends OncePerRequestFilter {
    
    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();
    private RequestCache requestCache = new HttpSessionRequestCache();
    private RequestMatcher requestMatcher =
            new NegatedRequestMatcher(
                    new OrRequestMatcher(
                            new AntPathRequestMatcher("/css/**"),
                            new AntPathRequestMatcher("/js/**"),
                            new AntPathRequestMatcher("/fonts/**"),
                            new AntPathRequestMatcher("/login/**")));

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(requestMatcher.matches(request) && trustResolver.isAnonymous(authentication)) {
            requestCache.saveRequest(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
