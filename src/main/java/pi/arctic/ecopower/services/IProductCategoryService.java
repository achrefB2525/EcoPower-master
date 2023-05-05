package pi.arctic.ecopower.services;

import jdk.jfr.Category;
import pi.arctic.ecopower.entities.ProductCategory;

import java.util.List;

public interface IProductCategoryService {
    public List<ProductCategory> findAllCategory();

    public ProductCategory findProdById(Long id);

    public ProductCategory addProduct(ProductCategory ProductCategory);

    public ProductCategory editProduct(ProductCategory ProductCategory);

    public void deleteProduct(Long id);


}
