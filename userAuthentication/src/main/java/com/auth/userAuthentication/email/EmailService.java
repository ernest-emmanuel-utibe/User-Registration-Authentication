package com.auth.userAuthentication.email;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    
    String sendMailWithAttachment(EmailDetails details);
}
