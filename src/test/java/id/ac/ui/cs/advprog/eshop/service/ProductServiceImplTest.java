package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void testCreate() {
        Product product = new Product();
        Product result = productService.create(product);
        assertEquals(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void testDeleteById() {
        String id = "id-123";
        productService.deleteById(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    void editProduct_updatesFields_whenProductExists() {
        Product existing = new Product();
        existing.setProductId("id-1");
        existing.setProductName("Old Name");
        existing.setProductQuantity(10);
        given(productRepository.findById("id-1")).willReturn(Optional.of(existing));

        Product update = new Product();
        update.setProductId("id-1");
        update.setProductName("New Name");
        update.setProductQuantity(25);

        Product result = productService.editProduct(update);

        assertNotNull(result);
        assertEquals("id-1", result.getProductId());
        assertEquals("New Name", result.getProductName());
        assertEquals(25, result.getProductQuantity());
        verify(productRepository).findById("id-1");
    }

    @Test
    void editProduct_throwsWhenProductNotFound() {
        given(productRepository.findById(anyString())).willReturn(Optional.empty());

        Product update = new Product();
        update.setProductId("missing");
        update.setProductName("Whatever");
        update.setProductQuantity(1);

        assertThrows(NoSuchElementException.class, () -> productService.editProduct(update));
        verify(productRepository).findById("missing");
    }

    @Test
    void testFindAll() {
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> productList = List.of(p1, p2);
        given(productRepository.findAll()).willReturn(productList.iterator());

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("id-123");
        given(productRepository.findById("id-123")).willReturn(Optional.of(product));

        Optional<Product> result = productService.findById("id-123");

        assertTrue(result.isPresent());
        assertEquals("id-123", result.get().getProductId());
        verify(productRepository).findById("id-123");
    }
}