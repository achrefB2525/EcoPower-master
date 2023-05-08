package pi.arctic.ecopower.services;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import pi.arctic.ecopower.auth.RegisterRequest;
import pi.arctic.ecopower.auth.AuthenticationRequest;
import pi.arctic.ecopower.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.Security.IJwtService;
import pi.arctic.ecopower.entities.Role;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.UserRepo;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final IUserservice iUserservice;
    private final IJwtService ijwtService;

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException, GeneralSecurityException {
        String message;
        String Subject;
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
        userRepo.save(user);
        Map<String, Object> claims = new HashMap<>();
        String code =  iUserservice.genratOtp();
        claims.put("code", code);
        claims.put("roles", user.getRole().toString());
        claims.put("id", user.getId());
        var JwtToken=ijwtService.generateToken(claims, user);;
        if (request.getRole().equals(Role.Provider)) {
            List<String> adminEmails = iUserservice.findAdminEmails();
            for (String email : adminEmails) {
                Subject = " Please verify your registration";

                message = "Un nouveau fournisseur vient de créer un compte. Veuillez confirmer ce compte s'il vous plaît.";
                iUserservice.sendEmail(email, Subject, message);




            }
        } else {
            JwtToken = ijwtService.generateToken(claims, user);

            iUserservice.sendEmail(user.getEmail(),"proposal for a contract",code);

        }

        return AuthenticationResponse.builder()
                .token(JwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            User user = userRepo.findByEmail(request.getEmail()).get();
            if (user != null) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", user.getRole().toString());
                claims.put("id", user.getId());
                var jwtToken = ijwtService.generateToken(claims, user);

                return AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build();
            }
        }
        return null;
    }



}