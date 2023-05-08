package pi.arctic.ecopower.services;



import com.sun.mail.util.MailSSLSocketFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.DTO.Payment;
import pi.arctic.ecopower.config.MailConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.Properties;
@RequiredArgsConstructor
@Service
public class EmailService  implements  IEmailService{

    @Autowired
    MailConfig mailConfig;
    @Autowired
    Session session;

    public void sendSimpleMessage(String to, String subject, String text)throws MessagingException{


        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
    public void sendEmail(String to, String subject, String text) throws MessagingException, GeneralSecurityException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "techmastertechmaster57@gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "true");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("techmastertechmaster57@gmail.com", "dlhhdkihksghysai");
                    }
                });

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        props.put("mail.smtp.ssl.enable", "true");


        // Ajout des logs pour le débogage
        session.setDebug(true);
        Transport transport = session.getTransport("smtp");
        transport.connect();

        Message message = new MimeMessage(session);
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(text);

        Transport.send(message);
    }












    @Override
    public void sendPaymentReceiptEmail(String recipientEmail, Payment payment) {

    }



}
