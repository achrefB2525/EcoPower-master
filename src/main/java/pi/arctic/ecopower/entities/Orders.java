package pi.arctic.ecopower.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long OrderId;
    long totalPrice;
    @ColumnDefault("0")
    Integer orderStatus;
    @CreationTimestamp
    LocalDateTime createdDate;
    @UpdateTimestamp
    LocalTime updateTime;
    @Column(name="order-tracking-number")
    String OrderTrackingNumber;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "orderMain")
   private Set<OrderItem> orderItems = new HashSet<>();
    @ManyToOne
    User user;

//    @Override
//    public String toString() {
//        return "Orders{" +
//
//                ", totalPrice=" + totalPrice +
//                ", orderStatus=" + orderStatus +
//                ", createdDate=" + createdDate +
//                ", updateTime=" + updateTime +
//                ", orderItems=" + orderItems +
//                ", user=" + user +
//                '}';
//    }
    //applies a mapping operation to each Product item in the stream. For each item, we get its price and multiply it by the quantity of that item in the cart.
    //This gives us the total cost of that item in the cart.
    // .reduce : performs a reduction operation on the stream. It adds up all the total costs of each item in the cart to get the total cost of all items in the cart.


  /*  @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    public Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    public Address billingAddress;
*/
    public void add(OrderItem item) {

        if (item != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }

            orderItems.add(item);
            item.setOrderMain(this);
        }
    }


}
