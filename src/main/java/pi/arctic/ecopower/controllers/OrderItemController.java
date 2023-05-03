package pi.arctic.ecopower.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.services.IOrderItemService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    private IOrderItemService iOrderItemService;

    @PostMapping
    void add(@RequestBody OrderItem item){
        iOrderItemService.add(item);
    }
    @PutMapping
    OrderItem update(OrderItem item){
        return iOrderItemService.update(item);
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
