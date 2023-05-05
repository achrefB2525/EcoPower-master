package pi.arctic.ecopower.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.arctic.ecopower.entities.ProductCategory;
import pi.arctic.ecopower.services.IProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("category")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryProductController {
    private IProductCategoryService iProductCategoryService;


    @GetMapping("/all")
    public ResponseEntity<List<ProductCategory>> getAllCategories () {
        List<ProductCategory> category = iProductCategoryService.findAllCategory();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ProductCategory> getCategoryById (@PathVariable("id") Long id) throws Exception {
        ProductCategory category = iProductCategoryService.findProdById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductCategory> addCategory(@RequestBody ProductCategory category) {
        ProductCategory newCategory = iProductCategoryService.addProduct(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductCategory> updateCategory(@RequestBody ProductCategory category) {
        ProductCategory updateCategory = iProductCategoryService.editProduct(category);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        iProductCategoryService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
