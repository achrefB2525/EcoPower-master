package pi.arctic.ecopower.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import pi.arctic.ecopower.dto.Payment;
import pi.arctic.ecopower.dto.Purchase;
import pi.arctic.ecopower.dto.PurchaseResponse;

public interface ICheckoutService {
    PurchaseResponse placeOrder(Purchase purchase) throws Exception;
    PaymentIntent createPaymentIntent(Payment paymentInfo) throws StripeException;

}
