package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @TestConfiguration
    static class MockConfig {
        @Bean
        ProductService productService() {
            return org.mockito.Mockito.mock(ProductService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Test
    void getCreate_returnsCreateProductViewWithModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void postCreate_createsProductAndRedirectsToList() throws Exception {
        given(productService.create(any(Product.class))).willAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/product/create")
                        .param("productName", "Test Item")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService).create(any(Product.class));
    }

    @Test
    void getList_returnsProductListViewWithProducts() throws Exception {
        Product p1 = new Product();
        p1.setProductId("id-1");
        p1.setProductName("Item A");
        p1.setProductQuantity(1);
        Product p2 = new Product();
        p2.setProductId("id-2");
        p2.setProductName("Item B");
        p2.setProductQuantity(2);
        given(productService.findAll()).willReturn(List.of(p1, p2));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is("id-1")),
                                hasProperty("productName", is("Item A")),
                                hasProperty("productQuantity", is(1))
                        )
                )));
    }

    @Test
    void getDelete_callsServiceAndRedirects() throws Exception {
        mockMvc.perform(get("/product/delete/{id}", "id-1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService).deleteById("id-1");
    }

    @Test
    void getEdit_existingProduct_returnsEditViewWithModel() throws Exception {
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("Item A");
        p.setProductQuantity(1);
        given(productService.findById("id-1")).willReturn(Optional.of(p));

        mockMvc.perform(get("/product/edit/{id}", "id-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", hasProperty("productId", is("id-1"))));
    }

    @Test
    void getEdit_nonExistingProduct_redirectsToList() throws Exception {
        given(productService.findById("missing")).willReturn(Optional.empty());

        mockMvc.perform(get("/product/edit/{id}", "missing"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void postEdit_updatesProductAndRedirectsToList() throws Exception {
        Product p = new Product();
        p.setProductId("id-1");
        p.setProductName("Updated");
        p.setProductQuantity(99);
        given(productService.editProduct(any(Product.class))).willReturn(p);

        mockMvc.perform(post("/product/edit")
                        .param("productId", "id-1")
                        .param("productName", "Updated")
                        .param("productQuantity", "99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService).editProduct(any(Product.class));
    }
}
