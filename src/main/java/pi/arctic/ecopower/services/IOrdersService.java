package pi.arctic.ecopower.services;

import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface IOrdersService {
    void add(Orders o);
    Orders update(Orders o);
    List<Orders> getAll();
    Orders getById(long id);
    void remove(long id);
    //Set<Orders> getOrdersContainingProduct(Product product);
    Set<Orders> getOrdersByDate(LocalDate date);
    long TotalPrice(Long idI);
    void validateOrder(long orderId);
    Orders getUserOrder(int idU);


}
