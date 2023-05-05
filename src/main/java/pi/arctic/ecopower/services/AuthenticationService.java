package pi.arctic.ecopower.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.Security.IJwtService;
import pi.arctic.ecopower.auth.AuthenticationRequest;
import pi.arctic.ecopower.auth.AuthenticationResponse;
import pi.arctic.ecopower.auth.RegisterRequest;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.UserRepo;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final PasswordEncoder passwordEncoder;
private  final AuthenticationManager authenticationManager;
   private final UserRepo userRepo;
  private   final IJwtService ijwtService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user =  User.builder()
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
        var jwtToken = ijwtService.generateTokenWithoutExtraClaims(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();}



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
     if(authentication.isAuthenticated()){
         User user = userRepo.findByEmail(request.getEmail()).get();
        if(user !=null){
            var jwtToken = ijwtService.generateTokenWithoutExtraClaims(user);

         return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }}
        return null ;
    }
}