package pi.arctic.ecopower.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pi.arctic.ecopower.repositories.OrdersRepo;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(name = "email")

    private String email;
    private String password;
    private int phone;
    private String address;
    private String companyname;
    @Enumerated(STRING)

    private Role role;
    public boolean enabled ;



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
    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
   // private static Set<Order> orders = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Orders> orders = new HashSet<>();

    public void add(Orders order) {

        if (order != null) {

            if (orders == null) {
                orders = new HashSet<>();
            }

            orders.add(order);
            order.setUser(this);
        }
    }

}

