package pi.arctic.ecopower.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "product name is required")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "give a description for your product")
    private String description;


    @Column(name = "price")
    @NotNull(message = "price must be done")
    private long prix;

    @Column(name = "quantity")
    @NotNull(message = "quantity must be done")
    private long quantity;

    @Column(name = "picture")
    private String picture;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updatedDate;

    private Double etoile;
    @ElementCollection
    private Map<Long, Double> clientEtoile;

    @ManyToOne
    ProductCategory category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productimage")
    Set<MultiPictures> multiPictures;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
            @JsonIgnore
    Set<OrderItem>orderItems;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "WISHLIST", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
    private Set<User> whoWhishesThisProduct;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", quantity=" + quantity +
                ", picture='" + picture + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", etoile=" + etoile +
                ", clientEtoile=" + clientEtoile +
                ", category=" + category +
                ", multiPictures=" + multiPictures +
                ", orderItems=" + orderItems +
                ", whoWhishesThisProduct=" + whoWhishesThisProduct +
                '}';
    }

//
}
