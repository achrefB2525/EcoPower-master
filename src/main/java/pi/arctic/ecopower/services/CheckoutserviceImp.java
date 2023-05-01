package pi.arctic.ecopower.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.DTO.Achat;
import pi.arctic.ecopower.entities.*;
import pi.arctic.ecopower.DTO.Payment;
import pi.arctic.ecopower.DTO.Reponseachat;
import pi.arctic.ecopower.repositories.UserRepo;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CheckoutserviceImp implements  ICheckoutservice {
     private  UserRepo userRepo;
    @Autowired
    IProductService productService;
    @Autowired
    IUserservice iUserservice ;
    public CheckoutserviceImp(UserRepo userRepo ,IProductService productService, @Value("${stripe.key.secret}") String secretKey) {
       this.userRepo =userRepo;
        this.productService = productService;
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(Payment payment) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", payment.getAmount());
        params.put("currency", payment.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "achat");
        params.put("receipt_email", payment.getEmailrecu());

        return PaymentIntent.create(params);
    };

    @Override
    public Reponseachat placeOrder(@NotNull HttpServletRequest Request ,Achat achat) {
        // recuperation des info de la commande
       /* Order order = new Order(
                1L,                    // id
                5,                     // totalQuantity
                100.0,                 // totalPrice
                "ABC123",              // orderTrackingNumber
                LocalDate.now(),       // createdDate
                new HashSet<>(),       // orderItems
                new HashSet<>(),       // products
                null                   // user
        );
        */

        // Adding order items (assuming you have a valid OrderItem instance)
       // OrderItem orderItem = new OrderItem();
        //order.add(orderItem);
         Order order = achat.getOrder();
        // generer un num de suivie de la commande
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);
      //liaison de commande au user
         User user = iUserservice.getUserByToken(Request);
         order.setUser(user);
         //liaison entre order et l'addess de liv
        order.setAddressLivraison(achat.getAddressLivraison());
        // populate order with orderItems
        Set<OrderItem> orderItems = achat.getOrder().getOrderItems();
        orderItems.forEach(item -> {
            Product newProduct = new Product();
            try {
                newProduct = productService.findProdById(item.getProductId());
                newProduct.setQuantity(newProduct.getQuantity() - item.getQuantity());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            order.add(item);

        });



        // Ajout de l'historique d'achat
        Historique historique = new Historique();
        historique.setOrderId(order.getId());
        historique.setUserId(user.getId());
        IHistoriqueAchatService historiqueService = new IHistoriqueAchatServiceImp(); // Créer une instance de IHistoriqueAchatServiceImp
        historiqueService.add(historique);

        return new Reponseachat(orderTrackingNumber);
    }


    private static int orderCount = 0;

    private String generateOrderTrackingNumber() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String orderNumber = String.format("%06d", orderCount);
        orderCount++;
        return timestamp + orderNumber;
    }
}

