package pi.arctic.ecopower.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pi.arctic.ecopower.entities.MultiPictures;
import pi.arctic.ecopower.entities.Product;
import pi.arctic.ecopower.entities.ProductCategory;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MultiPicturesRepo extends JpaRepository<MultiPictures,Long> {

    ArrayList<MultiPictures> findByProductimage(Product product);
    Set<MultiPictures> findMultiPicturesByProductimage(long idimg);

}
