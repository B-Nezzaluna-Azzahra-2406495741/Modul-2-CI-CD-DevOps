package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository implements ProductRepositoryInterface {
    private List<Product> productData = new ArrayList<>();

    // create product
    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(java.util.UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    // delete product
    public void deleteById(String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }

    // get all products
    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    // get product by id
    public Optional<Product> findById(String productId) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst();
    }
}