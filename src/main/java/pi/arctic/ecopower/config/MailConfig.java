package pi.arctic.ecopower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
    @Configuration
    public class MailConfig {

        @Bean
        public JavaMailSender javaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587); // or the appropriate port
            mailSender.setUsername("ecopowerr2023@gmail.com");
            mailSender.setPassword("Yassin20568767");

            return mailSender;
        }

    }

