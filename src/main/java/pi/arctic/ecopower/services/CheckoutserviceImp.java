package pi.arctic.ecopower.services;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.Value;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.dto.Payment;
import pi.arctic.ecopower.dto.Purchase;
import pi.arctic.ecopower.dto.PurchaseResponse;
import pi.arctic.ecopower.entities.OrderItem;
import pi.arctic.ecopower.entities.Orders;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.UserRepo;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CheckoutServiceImp implements ICheckoutService {
    private ProductServiceImp productService;
    private UserRepo customerRepository;

    public CheckoutServiceImp(UserRepo customerRepository, ProductServiceImp productService) {
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // retrieve the order info from dto
        Orders order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> {
            Product newProduct = new Product();
            try {
                newProduct = productService.findProdById(item.getProduct().getId());
                newProduct.setQuantity(newProduct.getQuantity() - item.getProduct().getQuantity());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            order.add(item);

        });
//        // populate order with billingAddress and shippingAddress
//        order.setBillingAddress(purchase.getBillingAddress());
//        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        User customer = purchase.getCustomer();
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
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


    private String generateOrderTrackingNumber() {

        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();


        // For details see: https://en.wikipedia.org/wiki/Universally_unique_identifier
        //

    }




}
