package pi.arctic.ecopower.services;

import lombok.NonNull;
import pi.arctic.ecopower.auth.RegisterRequest;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IUserservice {
    User add(RegisterRequest request);
    User getUserByToken(@NonNull HttpServletRequest request);
    void remove(int id);
    public void  confirm(User U);
    String genratOtp();
    void updateUser(User u) ;
    List<User> retrieveAllUsers();

    List<String> findAdminEmails();

    User getUserById(int id);
    void sendEmail(String to, String subject, String html) throws MessagingException;
}
