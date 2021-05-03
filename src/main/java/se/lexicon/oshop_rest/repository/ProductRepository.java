package se.lexicon.oshop_rest.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.oshop_rest.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findByNameIgnoreCase(String name);

    List<Product> findByProductDetails_DescriptionIgnoreCase(String description);
}
