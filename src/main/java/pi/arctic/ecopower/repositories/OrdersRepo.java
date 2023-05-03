package pi.arctic.ecopower.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.arctic.ecopower.entities.Orders;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface OrdersRepo extends JpaRepository<Orders,Long> {

    Set<Orders> findByCreatedDate(LocalDate date);
    //Page<Orders> findAllByOrderStatusOrderByCreatedDateDesc(Integer orderStatus, Pageable pageable);
    //Page<Orders> findAllByBuyerEmailOrderByOrderStatusAscCreatedDateDesc(String buyerEmail, Pageable pageable);
}
