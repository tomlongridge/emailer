package org.bathbranchringing.emailer.tasks;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Emailer {
    
    @Value("${notifications.smtp.server}")
    private String smtpServer;
    
    @Value("${notifications.smtp.username}")
    private String smtpUsername;
    
    @Value("${notifications.smtp.password}")
    private String smtpPassword;
    
    @Value("${notifications.senderAddress}")
    private String senderAddress;
    
    @Scheduled(fixedDelay = 10000000)
    public void sendNotifications() {
        
        // Session configuration
        
        final Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpServer);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        final Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smtpUsername, smtpPassword);
                    }
                });

        try {
            
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(senderAddress));

           message.addRecipient(Message.RecipientType.TO, new InternetAddress("tomlongridge@gmail.com"));
           message.setSubject("This is the Subject Line!");
           message.setText("This is actual message");

           Transport.send(message);
           
        } catch (MessagingException mex) {
           mex.printStackTrace();
        }
        
    }

}
