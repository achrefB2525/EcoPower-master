package pi.arctic.ecopower.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepo extends JpaRepository<Product,Long> {

    List<Product>findByPrixBetween(long minP,long maxP);

    List<Product> findByCategory(ProductCategory category);




}
