package pi.arctic.ecopower.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.DTO.Payment;

@Service
public class EmailService  implements  IEmailService{
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPaymentReceiptEmail(String recipientEmail, Payment payment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Payment Receipt");
        message.setText("Thank you for your payment.\n\nAmount: " + payment.getAmount()
                + "\nCurrency: " + payment.getCurrency() + "\n\nRegards,\nYour App");

        javaMailSender.send(message);
    }
}
