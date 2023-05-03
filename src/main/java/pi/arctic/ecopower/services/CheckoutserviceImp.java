package pi.arctic.ecopower.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.DTO.Purchase;
import pi.arctic.ecopower.entities.*;
import pi.arctic.ecopower.DTO.Payment;
import pi.arctic.ecopower.DTO.Reponseachat;
import pi.arctic.ecopower.repositories.UserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CheckoutserviceImp implements  ICheckoutservice {
    @Autowired
    UserRepo userRepo;
    @Autowired
    IProductService productService;
    @Autowired
    IUserservice iUserservice ;
    public CheckoutserviceImp(UserRepo userRepo , IProductService productService, @Value("${stripe.key.secret}") String secretKey) {
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

        return PaymentIntent.create(params);
    };
@Transactional
@Override
    public Reponseachat placeOrder(@NotNull HttpServletRequest Request , Purchase purchase) {
         Orders orders = purchase.getOrder();
        // generer un num de suivie de la commande
        String orderTrackingNumber = generateOrderTrackingNumber();
        orders.setOrderTrackingNumber(orderTrackingNumber);
        //populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> orders.add(item));
    //liaison entre order et l'addess de liv
  //  orders.setBillingAddress(purchase.getBillingAddress());
//    orders.setShippingAddress(purchase.getShippingAddress());


    //liaison de commande au user
         User user = iUserservice.getUserByToken(Request);
         orders.setUser(user);
         //save to database
        userRepo.save(user);
      //  Order savedOrder = orderRepo.save(order);


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

