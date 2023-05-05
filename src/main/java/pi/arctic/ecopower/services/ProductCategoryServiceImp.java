package pi.arctic.ecopower.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.entities.ProductCategory;
import pi.arctic.ecopower.repositories.ProductCategoryRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImp implements IProductCategoryService{
    private ProductCategoryRepo categoryProductRepo;
    @Override
    public List<ProductCategory> findAllCategory() {
        return categoryProductRepo.findAll();
    }

    @Override
    public ProductCategory findProdById(Long id) {
        return categoryProductRepo.findById(id).orElse(null);
    }

    @Override
    public ProductCategory addProduct(ProductCategory categoryProduct) {
        return categoryProductRepo.save(categoryProduct);
    }

    @Override
    public ProductCategory editProduct(ProductCategory categoryProduct) {
        return categoryProductRepo.save(categoryProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        categoryProductRepo.deleteById(id);
    }




}
