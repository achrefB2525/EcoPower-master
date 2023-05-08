package pi.arctic.ecopower.services;

import org.springframework.mail.SimpleMailMessage;
import pi.arctic.ecopower.DTO.Payment;
import pi.arctic.ecopower.auth.AuthenticationResponse;
import pi.arctic.ecopower.entities.User;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

public interface IEmailService {
    void sendEmail(String to, String subject, String html) throws MessagingException, GeneralSecurityException;

    public void sendPaymentReceiptEmail(String recipientEmail, Payment payment);
    void sendSimpleMessage(String to, String subject, String text) throws MessagingException;
}
