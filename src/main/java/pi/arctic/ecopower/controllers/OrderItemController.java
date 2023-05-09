package pi.arctic.ecopower.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.OrderItemRepo;
import pi.arctic.ecopower.services.IOrderItemService;
import pi.arctic.ecopower.services.IUserservice;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    private IOrderItemService iOrderItemService;
    @Autowired
    OrderItemRepo orderItemRepo;
@Autowired
private IUserservice userservice ;
    @PostMapping
    void add(@RequestBody OrderItem item){
        iOrderItemService.add(item);
    }
    @PutMapping("/cout/decrement/{id}")
    OrderItem updateOrderItem(@PathVariable Long id){
         OrderItem ExistantItem = orderItemRepo.findById(id).orElseThrow(null);
         ExistantItem.setCount(ExistantItem.getCount()-1);
         return orderItemRepo.save(ExistantItem);
    }
    @GetMapping
    List<OrderItem> getAll(){
        return iOrderItemService.getAll();
    }
    @GetMapping("/{id}")
    OrderItem getById(long id){
        return iOrderItemService.getById(id);
    }
    @DeleteMapping("/{id}")
    void remove(long id){
        iOrderItemService.remove(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/addToCard/{idP}/{idU}")
    void assignProductToCart(@PathVariable long idP, @PathVariable int idU){
        System.out.println(idP);
        iOrderItemService.assignProductToCart(idP, idU);

    }

}
