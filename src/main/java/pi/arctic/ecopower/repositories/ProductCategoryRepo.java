package pi.arctic.ecopower.repositories;

import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductCategoryRepo extends JpaRepository<ProductCategory,Long> {
    //ProductCategory findByType(Integer categoryType);
//    List<ProductCategory> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> categoryTypes);
//    List<ProductCategory> findAllByOrderByCategoryType();
    Optional<ProductCategory> findProductCategoriesById(Long id);
}
