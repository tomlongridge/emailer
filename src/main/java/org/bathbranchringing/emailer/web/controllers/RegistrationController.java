package org.bathbranchringing.emailer.web.controllers;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.bathbranchringing.emailer.core.domain.User;
import org.bathbranchringing.emailer.core.repo.UserDAO;
import org.bathbranchringing.emailer.web.URLConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/" + URLConstants.REGISTER)
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class RegistrationController extends BaseController {
    
    private static final String PAGE_REGISTER = "/pages/register";

	private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String initialise(final ModelMap model) {
        LOG.debug("-> Register");
        model.addAttribute("editableUser", new User());
        LOG.debug("<- Register");
        return PAGE_REGISTER;
    }
    
	@RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
	public String newUser(@ModelAttribute("editableUser") @Valid final User user,
	                      final BindingResult bindingResult,
	                      final HttpSession session) {
	    
	    LOG.debug("-> New User {}", user.getEmailAddress());
	    
	    if (bindingResult.hasErrors()) {
            LOG.debug("{} validation error(s) in user registration", bindingResult.getErrorCount());
	        return PAGE_REGISTER;
	    } else if (!user.getPassword().equals(user.getConfirmPassword())) {
	        LOG.debug("Passwords mismatch during registration");
	        bindingResult.reject("confirmPassword", "Passwords do not match");
	        return PAGE_REGISTER;
	    } else if (userDAO.find(user.getEmailAddress()) != null) {
            LOG.debug("Email address {} already registered", user.getEmailAddress());
            bindingResult.reject("emailAddress", "The email address is already registered");
            return PAGE_REGISTER;
	    }
	    
	    LOG.debug("Encoding password");
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
        
	    LOG.debug("Persisting user");
	    user.setEnabled(true); // TODO: Password confirm?
	    user.setCreationDate(new Date());
        user.setModificationDate(user.getCreationDate());
	    userDAO.add(user);

        LOG.debug("Setting user {} as authenticated user", user.getEmailAddress());
        UsernamePasswordAuthenticationToken token = 
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        LOG.debug("<- New User");
        final SavedRequest redirectRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (redirectRequest != null) {
            return redirect(redirectRequest.getRedirectUrl());
        } else {
            return redirect(URLConstants.HOME);
        }
	}
}
