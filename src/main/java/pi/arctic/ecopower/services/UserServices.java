package pi.arctic.ecopower.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import pi.arctic.ecopower.Security.IJwtService;
import pi.arctic.ecopower.auth.RegisterRequest;
import pi.arctic.ecopower.entities.Role;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.UserRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserServices implements IUserservice {
    @Autowired
    UserRepo userRepo;

    private  final PasswordEncoder passwordEncoder;
    private final IJwtService IjwtService;

    private final JavaMailSender mailSender;



    @Override
    public void updateUser(User u) {
        if (userRepo.findById(u.getId()).isPresent())
            userRepo.save(u);
        else
            System.out.println("doesnt exist");
    }

    public void  confirm(User U){
        U.setEnabled(true);}
    @Override
    public User add(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUserName())
                .completname(request.getCompletname())
                .cin(request.getCin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .address(request.getAddress())
                .companyname(request.getCompanyname())
                .build();
        //  if(userRepo.findByEmail(user.getEmail()).isPresent()){
        //    throw new EmailExist("email exist");}
        return         userRepo.save(user);

    }

    @Override
    public void remove(int id) {
        userRepo.delete(userRepo.findById(id).get());
    }
    @Override
    public List<User> retrieveAllUsers() {
        return  userRepo.findAll();



    }
    @Override
    public List<String> findAdminEmails() {
        List<String> adminEmails = new ArrayList<>();
        List<User> adminUsers = userRepo.findAll().stream()
                .filter(user -> user.getRole() == Role.Admin)
                .collect(Collectors.toList());
        for (User user : adminUsers) {
            adminEmails.add(user.getEmail());
        }
        return adminEmails;
    }
    @Override
    public   String genratOtp(){
        return new DecimalFormat("0000").format(new Random().nextInt(9999));

    }

    @Override
    public User getUserById(int id) {
        return userRepo.findById(id).get();
    }
    @Override
    public User getUserByToken(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        jwt = authHeader.substring(7);
        userEmail = IjwtService.extractUserEmail(jwt);

        return userRepo.findByEmail(userEmail).get();
    }

    public void sendEmail(String to, String subject, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }


}
