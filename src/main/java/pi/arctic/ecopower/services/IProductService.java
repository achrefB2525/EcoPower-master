package pi.arctic.ecopower.services;

import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    List<Product> findAllProducts();
    Product findProdById(Long id);
    long addProduct(Product product);
    long editProduct(Product product);
    void deleteProduct(Long id);
    List<Product> getAllProductByCategory(ProductCategory category);
    void calculeEtoile(Double rev, Long idP, int idC) throws Exception;

    List<Product>findByPrice(long minP, long maxP);

}
