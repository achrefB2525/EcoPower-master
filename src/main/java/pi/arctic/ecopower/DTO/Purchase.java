package pi.arctic.ecopower.DTO;

import pi.arctic.ecopower.entities.Address;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.OrderItem;
import lombok.Data;
import pi.arctic.ecopower.entities.User;


import java.util.Set;
@Data
public class Purchase {
    private User user;
    private Address shippingAddress;
    private Address billingAddress;
    private Orders order;
    private Set<OrderItem> orderItems;


}
