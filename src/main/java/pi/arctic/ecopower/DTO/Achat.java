package pi.arctic.ecopower.dto;

import pi.arctic.ecopower.entities.Address;

import com.stripe.model.OrderItem;
import lombok.Data;
import org.apache.catalina.User;
import pi.arctic.ecopower.entities.Orders;


import java.util.Set;
@Data
public class Achat {
    User user;
    private Address addressLivraison;
    private Set<OrderItem> getOrderItems;
    private Orders order ;
}
