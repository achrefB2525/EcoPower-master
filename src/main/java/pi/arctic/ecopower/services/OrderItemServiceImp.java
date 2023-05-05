package pi.arctic.ecopower.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.User;
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

    @Override
    public void add(OrderItem item) {
        orderItem.save(item);
    }

    @Override
    public OrderItem update(OrderItem item) {

        return orderItem.save(item);
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
        System.out.println(idP);
        boolean reponse = false;
        boolean found = false;

        for (Orders orders : user.getOrders()) {
            System.out.println(orders);
            if (orders.getOrderStatus() == 0) {
                found = true;
                for (OrderItem item : orders.getOrderItems()) {
                    if (item.getProduct().getId().equals(idP)) {
                        item.setCount(item.getCount() + 1); //incrémenter nb OrderItem
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
           // order.setOrderId(2L);
            System.out.println(order);
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


}
