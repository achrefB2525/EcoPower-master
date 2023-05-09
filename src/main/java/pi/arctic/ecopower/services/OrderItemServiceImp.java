package pi.arctic.ecopower.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.enums.CartItemDoesNotExistsException;
import pi.arctic.ecopower.repositories.OrderItemRepo;
import pi.arctic.ecopower.repositories.OrdersRepo;
import pi.arctic.ecopower.repositories.ProductRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemServiceImp implements IOrderItemService {
    @Autowired
    private OrderItemRepo orderItem;
    @Autowired
    private ProductServiceImp prouductserviceImp;
    @Autowired
    private OrdersServiceImp ordersServiceImp;

    @Autowired
    private OrdersRepo ordersRepo;
    @Autowired
    private UserServices userService;
    private final OrderItemRepo orderItemRepo;

    @Override
    public void add(OrderItem item) {
        orderItem.save(item);
    }

    @Override
    public OrderItem update(OrderItem item) {
        return null;
    }


    @Override
    public List<OrderItem> getAll() {
        return orderItem.findAll();
    }

    @Override
    public OrderItem getById(long id) {
        return orderItem.findById(id).orElse(null);
    }

    @Override
    public void remove(long id) {
        orderItem.deleteById(id);

    }


    @Override
    public void assignProductToCart(long idP, int idU) {
        Product product = prouductserviceImp.findProdById(idP);
        User user = userService.getUserById(idU);
        System.out.println("this is a product id:" + idP);
        boolean reponse = false;
        boolean found = false;

        for (Orders orders : user.getOrders()) {
            System.out.println("this is the user orders: "+ orders);
            if (orders.getOrderStatus() == 0) {
                found = true;
                for (OrderItem item : orders.getOrderItems()) {
                    if (item.getProduct().getId().equals(idP)) {
                        System.out.println("********  il a trouvé un produit sémilaire dans l'item : "+ item.getCount());
                        item.setCount(item.getCount() + 1); //incrémenter nb OrderItem
                        System.out.println("******** New Count :" + item.getCount());
                        orderItem.save(item);
                        reponse = true;
                    }
                }
                if (reponse != true) {
                    OrderItem o = new OrderItem();
                    o.setProduct(product);
                    o.setCount(1);
                    orders.getOrderItems().add(o);
                    o.setOrderMain(orders);
                    orderItem.save(o);
                    ordersRepo.save(orders);

                }
            }
        }
        System.out.println(found);
        if (found != true) {
            Orders order = new Orders();
            order.setUser(user);
            order.setOrderStatus(0);
<<<<<<< Updated upstream
           // order.setOrderId(2L);
            System.out.println(order);
=======
            // order.setOrderId(2L);
            System.out.println("this is the new order created :"+ order);
>>>>>>> Stashed changes
            OrderItem o = new OrderItem();
            o.setProduct(product);
            o.setCount(1);
            orderItem.save(o);
            //initialisation l Set<OrderItems> = new hashset puis affecteha l order
            order.getOrderItems().add(o);
            o.setOrderMain(order);

            System.out.println("------------------------");
            System.out.println(order);

            ordersRepo.save(order);


        }
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        for (OrderItem item : getAll()) {
            if (item.equals(orderItem)) {
                item.setCount(orderItem.getCount());
                return orderItemRepo.save(item);
            }
        }

        throw new CartItemDoesNotExistsException(
                "Cart item w/ user id " + " and product id " +
                        orderItem.getProduct().getId() + " does not exist."
        );
    }
}
