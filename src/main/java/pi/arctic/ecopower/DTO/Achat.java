package pi.arctic.ecopower.DTO;

import pi.arctic.ecopower.entities.Address;
import pi.arctic.ecopower.entities.Order;
import com.stripe.model.OrderItem;
import lombok.Data;
import org.apache.catalina.User;


import java.util.Set;
@Data
public class Achat {
    User user;
    private Address addressLivraison;
    private Set<OrderItem> getOrderItems;
    private Order order ;
}
