package pi.arctic.ecopower.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.EnumType.STRING;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity


public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  int id ;
    private int cin;

    private String username;

    private String completname;
    private String email;
    private String password;
    private int phone;
    private String address;
    private String companyname;
    @Enumerated(STRING)
    private Role role;

    public boolean enabled =true;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
@Override
public String getPassword(){
        return password;
}
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override

    public boolean isCredentialsNonExpired() {
        return true;
    }
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private static Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
            order.setUser(this);
        }
    }
}

