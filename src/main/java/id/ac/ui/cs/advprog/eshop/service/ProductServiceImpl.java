package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // create product
    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }
    // delete product by id
    @Override
    public void deleteById(String productId) {
        productRepository.deleteById(productId);
    }

    // edit product
    @Override
    public Product editProduct(Product product) {
        Optional<Product> productExist = productRepository.findById(product.getProductId());
        if (productExist.isPresent()) {
            Product updatedProduct = productExist.get();
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setProductQuantity(product.getProductQuantity());
            return updatedProduct;
        }
        throw new RuntimeException("Product not found with ID: " + product.getProductId());
    }

    // find all products
    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }

    // find product by id
    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }
}