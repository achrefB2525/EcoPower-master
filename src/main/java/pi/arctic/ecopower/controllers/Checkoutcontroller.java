package pi.arctic.ecopower.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.sun.istack.NotNull;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pi.arctic.ecopower.DTO.Achat;
import pi.arctic.ecopower.DTO.Payment;
import pi.arctic.ecopower.DTO.Reponseachat;
import pi.arctic.ecopower.services.CheckoutserviceImp;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/checkout")
public class Checkoutcontroller {
    @Autowired
    CheckoutserviceImp checkoutservice ;
    @PostMapping("/achat")
    //passer une commande et la triater
 public Reponseachat placeorder(@NotNull HttpServletRequest Request , @RequestBody Achat achat ) throws Exception {
        Reponseachat repenseachat = checkoutservice.placeOrder(Request,achat);
        return repenseachat;
    }
    @PostMapping("/payment")
    public ResponseEntity<String>createPaymentIntent(@RequestBody Payment payment )throws StripeException{
        PaymentIntent paymentIntent =checkoutservice.createPaymentIntent(payment);
        String payments = paymentIntent.toJson();
        return new ResponseEntity<>(payments , HttpStatus.OK);
    }

}
