package pi.arctic.ecopower.entities;


import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private  Set<Orders> orders = new HashSet<>();



    public void add(Orders order) {

        if (order != null) {

            if (orders == null) {
                orders = new HashSet<>();
            }

            orders.add(order);
            order.setUser(this);
        }
    }
    @ManyToMany(mappedBy = "whoWhishesThisProduct")
    public Set<Product> productsWished;

//    @Override
//    public String toString() {
//        return "User{" +
//                "cin=" + cin +
//                ", username='" + username + '\'' +
//                ", completname='" + completname + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", phone=" + phone +
//                ", address='" + address + '\'' +
//                ", companyname='" + companyname + '\'' +
//                ", role=" + role +
//                ", enabled=" + enabled +
//                ", orders=" + orders +
//                ", productsWished=" + productsWished +
//                '}';
//    }
}
