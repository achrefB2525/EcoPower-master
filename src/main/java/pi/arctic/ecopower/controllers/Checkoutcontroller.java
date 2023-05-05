package pi.arctic.ecopower.controllers;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.arctic.ecopower.dto.Payment;
import pi.arctic.ecopower.dto.Purchase;
import pi.arctic.ecopower.dto.PurchaseResponse;
import pi.arctic.ecopower.services.CheckoutServiceImp;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {


    private final CheckoutServiceImp checkoutService;


    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) throws Exception {

        PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

        return purchaseResponse;
    }

    @PostMapping("/payment")
    public ResponseEntity<String> createPaymentIntent(@RequestBody Payment payment )throws StripeException {
        PaymentIntent paymentIntent =checkoutService.createPaymentIntent(payment);
        String payments = paymentIntent.toJson();
        return new ResponseEntity<>(payments , HttpStatus.OK);
    }

}
