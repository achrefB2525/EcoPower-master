package pi.arctic.ecopower.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.repositories.OrderItemRepo;
import pi.arctic.ecopower.repositories.OrdersRepo;
import pi.arctic.ecopower.repositories.ProductRepo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class OrdersServiceImp implements IOrdersService {
    @Autowired
    private  OrdersRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public void add(Orders o) {
        orderRepo.save(o);
    }

    @Override
    public Orders update(Orders o) {
        return orderRepo.save(o);
    }

    @Override
    public List<Orders> getAll() {
        return orderRepo.findAll();
    }

    @Override
    public Orders getById(long id) {
        return orderRepo.findById(id).orElse(null);
    }

    @Override
    public void remove(long id) {
        orderRepo.deleteById(id);
    }

//    @Override
//    public Set<Orders> getOrdersContainingProduct(Product product) {
//        return orderRepo.findByProducts(product);
//    }

    @Override
    public Set<Orders> getOrdersByDate(LocalDate date) {
        return orderRepo.findByCreatedDate(date);
    }

    @Override
    public long TotalPrice(Long idI) {
        Orders orders=getById(idI);
        long price=0;
//        orders.getOrderItems().forEach(oi -> oi.getProductPrice().multiply(new BigDecimal (oi.getCount()));
        for(OrderItem orderItem: orders.getOrderItems()){
            price=price+(orderItem.getProduct().getPrix())*orderItem.getCount();
        }
        orders.setTotalPrice(price);
        orderRepo.save(orders);
        return  price;
    }
    @Override
    public void validateOrder(long orderId) {
        Orders order = getById(orderId);

        // Update the quantity of each product in the order
        for (OrderItem item: order.getOrderItems()) {
            Product product = item.getProduct();
            long quantity = item.getCount();
            long newQuantity = product.getQuantity() - quantity;
            if (newQuantity < 0) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }
            product.setQuantity(newQuantity);
            productRepo.save(product);
        }

        // Set the order status to "validated"
        order.setOrderStatus(1);
        orderRepo.save(order);
    }




}
