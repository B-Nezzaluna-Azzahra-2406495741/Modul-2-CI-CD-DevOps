package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9896");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_whenProductExists() {
        // Arrange
        Product product = new Product();
        product.setProductId("p-123");
        product.setProductName("Shampoo");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Act
        var found = productRepository.findById("p-123");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("p-123", found.get().getProductId());
        assertEquals("Shampoo", found.get().getProductName());
        assertEquals(10, found.get().getProductQuantity());
    }

    @Test
    void testFindById_whenProductDoesNotExist() {
        // repository empty
        var found = productRepository.findById("does-not-exist");
        // Assert
        assertTrue(found.isEmpty());
    }

    @Test
    void testDeleteById_removesProduct() {
        // Arrange
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("A");
        product1.setProductQuantity(1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        product2.setProductName("B");
        product2.setProductQuantity(2);
        productRepository.create(product2);

        // sanity check
        Iterator<Product> beforeDelete = productRepository.findAll();
        assertTrue(beforeDelete.hasNext());

        // Act
        productRepository.deleteById("id-1");

        // only product2 remains
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product remaining = productIterator.next();
        assertEquals("id-2", remaining.getProductId());
        assertFalse(productIterator.hasNext());

        // findById for deleted returns empty
        assertTrue(productRepository.findById("id-1").isEmpty());
        assertTrue(productRepository.findById("id-2").isPresent());
    }
}