package pi.arctic.ecopower.dto;

import lombok.Data;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.User;

import java.util.Set;

@Data
public class Purchase {
    private User customer;
    private Orders order;
    private Set<OrderItem> orderItems;

}
