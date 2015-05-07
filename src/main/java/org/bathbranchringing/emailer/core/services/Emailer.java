package org.bathbranchringing.emailer.core.services;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Emailer {
    
    static final Logger LOG = LoggerFactory.getLogger(Emailer.class);
    
    @Value("${email.enabled}")
    private boolean enabled;
    
    @Value("${email.smtp.server}")
    private String smtpServer;
    
    @Value("${email.smtp.username}")
    private String smtpUsername;
    
    @Value("${email.smtp.password}")
    private String smtpPassword;
    
    @Value("${email.sender.address}")
    private String senderAddress;
    
    @Value("${email.sender.name}")
    private String senderName;
    
    @Value("${email.subject.prefix}")
    private String subjectPrefix;
    
    private Session session = null;
    
    public void send(final String address, final String to, final String subject, final String body) {
        
        try {
            
           final MimeMessage message = new MimeMessage(getSession());
           message.setFrom(new InternetAddress(senderAddress, senderName));
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(address, to));
           message.setSubject(subjectPrefix + subject);
           message.setText(body);
           
           LOG.info("Sending mail to " + to);
           
           if (enabled) {
               Transport.send(message);
           } else {
               LOG.info("No email sent - service disabled");
               LOG.debug("Subject: " + subject);
               LOG.debug("Body: " + body);
           }
           
        } catch (SendFailedException e) {
           LOG.error("Unable to send email to recipient.", e);
        } catch (UnsupportedEncodingException e) {
            LOG.error("Unable to send email to recipient due to bad address.", e);
        } catch (MessagingException e) {
           LOG.error("Unable to send email due to server error.", e);
        }
    
    }

    private Session getSession() {

        if (session == null) {
            session = Session.getDefaultInstance (
                          getProperties(),
                          new javax.mail.Authenticator() {
                              protected PasswordAuthentication getPasswordAuthentication() {
                                  return new PasswordAuthentication(smtpUsername, smtpPassword);
                              }
                          });
        }
        return session;
    }
    
    public Properties getProperties() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpServer);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        return properties;
    }

}
