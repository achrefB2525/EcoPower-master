package pi.arctic.ecopower.services;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;
import pi.arctic.ecopower.entities.User;
import pi.arctic.ecopower.repositories.ProductRepo;
import pi.arctic.ecopower.repositories.UserRepo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductServiceImp implements IProductService{

    private final ProductRepo productRepo;

    private final UserRepo userRepo;


    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product findProdById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public long addProduct(Product product) {
        return productRepo.save(product).getId();
    }

    @Override
    public long editProduct(Product product) {
        return productRepo.save(product).getId();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
    public List<Product> getAllProductByCategory(ProductCategory category) {
        return productRepo.findByCategory(category);
    }

    public Product updateProduit(Product u) {
        return productRepo.save(u);
    }

    public void calculeEtoile(Double rev, Long idP, int idC) throws Exception {

        Product p = findProdById(idP);
        Double rectif = null;
        User c = (User) userRepo.findById(idC).orElse(null);
        Map<Long, Double> temp = p.getClientEtoile();
        Double sum = 0.0;
        if (((p != null)) && (rev >= 0 && rev <= 5)) {
            temp.put((long)idC, rev);
            for (Double value : temp.values()) {
                sum += value;

            }
            rectif = sum / temp.size();
            if (rectif >= 0 && rectif < 0.5) {
                p.setEtoile(0.0);
                p.setClientEtoile(temp);
            } else if (rectif >= 0.5 && rectif < 1) {
                p.setEtoile(0.5);
                p.setClientEtoile(temp);
            } else if (rectif >= 1 && rectif < 1.5) {
                p.setEtoile(1.0);
                p.setClientEtoile(temp);
            } else if (rectif >= 1.5 && rectif < 2) {
                p.setEtoile(1.5);
                p.setClientEtoile(temp);
            } else if (rectif >= 2 && rectif < 2.5) {
                p.setEtoile(2.0);
                p.setClientEtoile(temp);
            } else if (rectif >= 2.5 && rectif < 3) {
                p.setEtoile(2.5);
                p.setClientEtoile(temp);
            } else if (rectif >= 3 && rectif < 3.5) {
                p.setEtoile(3.0);
                p.setClientEtoile(temp);
            } else if (rectif >= 3.5 && rectif < 4) {
                p.setEtoile(3.5);
                p.setClientEtoile(temp);
            } else if (rectif >= 4 && rectif < 4.5) {
                p.setEtoile(4.0);
                p.setClientEtoile(temp);
            } else if (rectif >= 4.5 && rectif < 4.75) {
                p.setEtoile(4.5);
                p.setClientEtoile(temp);
            } else if (rectif >= 4.75 && rectif <= 5) {
                p.setEtoile(5.0);
                p.setClientEtoile(temp);
            }
            updateProduit(p);
        }
    }

    @Override
    public List<Product> findByPrice(long minP, long maxP) {
        return productRepo.findByPrixBetween(minP,maxP);
    }

}
