package pi.arctic.ecopower.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.services.IOrdersService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private IOrdersService iOrderService;
    @PostMapping
    void add(@RequestBody Orders o){
        iOrderService.add(o);
    }
    @PutMapping
    Orders update(@RequestBody Orders o){
        return iOrderService.update(o);
    }
    @GetMapping("/all")
    List<Orders> getAll(){
        return iOrderService.getAll();
    }
    @GetMapping("/{id}")
    Orders getById(@PathVariable long id){
        return iOrderService.getById(id);
    }
    @DeleteMapping("/{id}")
    void remove(long id){
        iOrderService.remove(id);
    }
//    @GetMapping("/contProd/{id}")
//    Set<Orders> getOrdersContainingProduct(@PathVariable Product product){
//        return iOrderService.getOrdersContainingProduct(product);
//    }
    @GetMapping("/{date}")
    Set<Orders> getOrdersByDate(@PathVariable LocalDate date){

        return iOrderService.getOrdersByDate(date);
    }
    @PostMapping("/validate/{orderId}")
    public ResponseEntity<String> validateOrder(@PathVariable long orderId) {
        try {
            iOrderService.validateOrder(orderId);
            return ResponseEntity.ok("Order validated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while validating the order.");
        }
    }
}
