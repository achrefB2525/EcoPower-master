package pi.arctic.ecopower.controllers;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;
import pi.arctic.ecopower.entities.MultiPictures;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;
import pi.arctic.ecopower.repositories.MultiPicturesRepo;
import pi.arctic.ecopower.repositories.ProductRepo;
import pi.arctic.ecopower.services.ProductCategoryServiceImp;
import pi.arctic.ecopower.services.ProductServiceImp;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("product")
public class ProductController implements ServletContextAware {

    private final ProductServiceImp productService;
    private final ProductCategoryServiceImp productCategoryService;
    ServletContext context;
    private ProductRepo productRepository;
    @Autowired
    MultiPicturesRepo imageRepository;

    public ProductController(ProductServiceImp productService, ProductRepo productRepository, ProductCategoryServiceImp productCategoryService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.productRepository = productRepository;
    }


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    private String saveImage(MultipartFile multipartFile) {
        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(context.getRealPath("/Imagess/" + multipartFile.getOriginalFilename()));
            Files.write(path, bytes);
            return multipartFile.getOriginalFilename();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.context = servletContext;
    } //obtenir le chemin réel du système de fichiers du serveur où les images seront enregistrées

    @PostMapping("/addproduct")
    public long newProduct(@RequestParam("files") MultipartFile[] files,
                           @RequestParam("product") String product,
                           @RequestParam("file") MultipartFile image) throws JsonParseException, JsonMappingException, Exception {

        Product arti = new ObjectMapper().readValue(product, Product.class); //convertir la chaîne "product" en un objet "Product
        boolean isExit = new File(context.getRealPath("/Imagess/")).exists(); //vérifie si le dossier image existe
        if (!isExit) {
            new File(context.getRealPath("/Imagess/")).mkdir();
            System.out.println("mk dir Imagess.............");
        }
        System.out.println("Save Article  22222.............");
        Set<MultiPictures> photos = new HashSet<>();
        for (MultipartFile file : files) {                  //pour chacun, elle enregistre le fichier dans le dossier "/Imagess/" en utilisant le nom
            // de fichier d'origine
            MultiPictures fileinfo = new MultiPictures();
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath("/Imagess/" + File.separator + newFileName));
            try {
                System.out.println("Image");
                FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
            fileinfo.setName(newFileName);
            fileinfo.setProductimage(arti);
            imageRepository.save(fileinfo); //stocke les informations sur l'image dans la base
            photos.add(fileinfo);
        }
        String fileName = image.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(fileName) + "." + FilenameUtils.getExtension(fileName);
        File serverFile = new File(context.getRealPath("/Imagess/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, image.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Save Article 333333.............");
        arti.setPicture(newFileName);

        System.out.println("Save Article 333333.............");

        System.out.println(arti);
        return productService.addProduct(arti);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteproduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public long updateproduct(@RequestParam("files") MultipartFile[] files,
                              @RequestParam("product") String product,
                              @RequestParam("file") MultipartFile image) throws JsonParseException, JsonMappingException, Exception {

        Product arti = new ObjectMapper().readValue(product, Product.class);
        boolean isExit = new File(context.getRealPath("/Imagess/")).exists();
        if (!isExit) {
            new File(context.getRealPath("/Imagess/")).mkdir();
            System.out.println("mk dir Imagess.............");
        }
        System.out.println("Save Article  22222.............");
        Set<MultiPictures> photos = new HashSet<>();
        for (MultipartFile file : files) {
            MultiPictures fileinfo = new MultiPictures();
            String filename = file.getOriginalFilename();
            String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
            File serverFile = new File(context.getRealPath("/Imagess/" + File.separator + newFileName));
            try {
                System.out.println("Image");
                FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
            fileinfo.setName(newFileName);
            fileinfo.setProductimage(arti);
            imageRepository.save(fileinfo);
            photos.add(fileinfo);
        }
        String fileName = image.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(fileName) + "." + FilenameUtils.getExtension(fileName);
        File serverFile = new File(context.getRealPath("/Imagess/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, image.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        arti.setPicture(newFileName);




        return productService.editProduct(arti);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> getEmployeeById(@PathVariable("id") Long id) throws Exception {
        Product product = productService.findProdById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping(path = "/Imgarticles/{id}")
    public List<byte[]> getPhoto(@PathVariable("id") Long id) throws Exception {
        ArrayList<MultiPictures> files = new ArrayList<MultiPictures>();
        Product product = productService.findProdById(id);
        List<byte[]> fi = new ArrayList<>();
        files = imageRepository.findByProductimage(product);
        System.out.println(files);

        for (MultiPictures file : files) {
            fi.add(Files.readAllBytes(Paths.get(context.getRealPath("/Imagess/") + file.getName())));
        }

        return fi;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<List<MultiPictures>> getImagesByProduct(@PathVariable("id") Long id) throws Exception {
        ArrayList<MultiPictures> files = new ArrayList<MultiPictures>();
        Product product = productService.findProdById(id);
        files = imageRepository.findByProductimage(product);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping(path = "/getimage/{id}")
    public byte[] getPhotoProduct(@PathVariable("id") Long id) throws Exception {
        MultiPictures Article = imageRepository.findById(id).orElseThrow(() -> new Exception("File by id " + id + " was not found"));
        ;
        return Files.readAllBytes(Paths.get(context.getRealPath("/Imagess/") + Article.getName()));
    }

    @GetMapping("/catproducts/{id}")
    public ResponseEntity<List<Product>> getAllProductByCategory(@PathVariable("id") Long id) throws Exception {
        ProductCategory category = productCategoryService.findProdById(id);
        List<Product> products = productService.getAllProductByCategory(category);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/Imgarticle/{id}")
    public byte[] getProductImage(@PathVariable("id") Long id) throws Exception {
        Product Article = productService.findProdById(id);
        return Files.readAllBytes(Paths.get(context.getRealPath("/Imagess/") + Article.getPicture()));
    }

    @GetMapping("/add-etoile/{produit-id}/{client-id}/{rev}")
    @ResponseBody
    public void moyEtoile(@PathVariable("produit-id") Long produitId, @PathVariable("client-id") int clientId, @PathVariable("rev") Double rev) throws Exception {
        productService.calculeEtoile(rev, produitId, clientId);
    }

    @GetMapping("prix/{minP}/{maxP}")
    public List<Product> findByPrice(@PathVariable long minP, @PathVariable long maxP){
        return productService.findByPrice(minP,maxP);
    }


//    @Override
//    public void setServletContext(ServletContext servletContext) {
//        this.context = servletContext;
//    }
}
