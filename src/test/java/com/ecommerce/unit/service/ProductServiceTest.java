package com.ecommerce.unit.service;

import com.ecommerce.business.domain.Product;
import com.ecommerce.business.exception.NotFoundException;
import com.ecommerce.business.exception.ProcessingException;
import com.ecommerce.business.service.ProductService;
import com.ecommerce.database.repository.CartItemRepository;
import com.ecommerce.database.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductById() {
        // given
        Product product = Product.builder().id(1).name("Test").stock(10).build();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // when
        Product result = productService.getProductById(1);

        // then
        assertEquals(product, result);
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        // given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // when & then
        Throwable exception = assertThrows(NotFoundException.class,
                () -> productService.getProductById(1));
        assertEquals("Could not find product by id: [1]", exception.getMessage());
    }

    @Test
    void shouldGetProductsByName() {
        // given
        List<Product> products = List.of(
                Product.builder().id(1).name("Test").build(),
                Product.builder().id(2).name("Test 2").build()
        );
        when(productRepository.findByName("Test")).thenReturn(products);

        // when
        List<Product> result = productService.getProductsByName("Test");

        // then
        assertEquals(products, result);
    }

    @Test
    void shouldGetAllProducts() {
        // given
        List<Product> products = List.of(
                Product.builder().id(1).name("Test").build(),
                Product.builder().id(2).name("Test 2").build()
        );
        when(productRepository.findAll()).thenReturn(products);

        // when
        List<Product> result = productService.getAllProducts();

        // then
        assertEquals(products, result);
    }

    @Test
    void shouldAddProduct() {
        // given
        Product product = Product.builder().id(1).name("Test").build();
        when(productRepository.saveProduct(product)).thenReturn(product);

        // when
        Product result = productService.addProduct(product);

        // then
        assertEquals(product, result);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product existingProduct = Product.builder()
                .id(1)
                .name("Old")
                .description("Old Description")
                .price(BigDecimal.TEN)
                .stock(10)
                .build();

        Product incomingProduct = Product.builder()
                .name("New")
                .description("New Description")
                .price(BigDecimal.ONE)
                .stock(5)
                .build();

        Product updatedProduct = Product.builder()
                .name("New")
                .description("New Description")
                .price(BigDecimal.ONE)
                .stock(5)
                .build();
        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.updateProduct(eq(1), any())).thenReturn((updatedProduct));

        // when
        Product result = productService.updateProduct(1, incomingProduct);

        // then
        assertEquals("New", result.getName());
        assertEquals(5, result.getStock());
    }

    @Test
    void shouldDeleteProduct() {
        // when
        productService.deleteProduct(1);

        // then
        verify(cartItemRepository).deleteByProductId(1);
        verify(productRepository).deleteById(1);
    }

    @Test
    void shouldDecreaseStock() {
        // given
        Product product = Product.builder().id(1).stock(10).build();
        Product updatedProduct = Product.builder().stock(7).build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.updateProduct(eq(1), any())).thenReturn(updatedProduct);

        // when
        productService.decreaseStock(1, 3);

        // then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).updateProduct(eq(1), captor.capture());
        assertEquals(7, captor.getValue().getStock());
    }

    @Test
    void shouldThrowIfNotEnoughStock() {
        // given
        Product product = Product.builder().id(1).stock(2).build();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // when & then
        Throwable exception = assertThrows(ProcessingException.class,
                () -> productService.decreaseStock(1, 3));
        assertEquals("Not enough stock for product with id: [%s]".formatted(product.getId()), exception.getMessage());
    }

    @Test
    void shouldIncreaseStock() {
        Product product = Product.builder().id(1).stock(5).build();
        Product updatedProduct = Product.builder().id(1).stock(8).build();

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.updateProduct(eq(1), any())).thenReturn(updatedProduct);

        // when
        productService.increaseStock(1, 3);

        // then
        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).updateProduct(eq(1), captor.capture());
        assertEquals(8, captor.getValue().getStock());
    }
}