package pi.arctic.ecopower.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pi.arctic.ecopower.entities.User;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository< User, Integer> {
    Optional<User> findByEmail(String email);

}