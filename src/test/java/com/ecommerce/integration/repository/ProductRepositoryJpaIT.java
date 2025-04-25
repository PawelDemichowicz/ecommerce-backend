package com.ecommerce.integration.repository;

import com.ecommerce.business.domain.Product;
import com.ecommerce.database.entity.mapper.ProductEntityMapperImpl;
import com.ecommerce.database.repository.ProductRepository;
import com.ecommerce.integration.repository.configuration.AbstractJpaIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import({ProductRepository.class, ProductEntityMapperImpl.class})
public class ProductRepositoryJpaIT extends AbstractJpaIT {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProductByIdAndName() {
        // given
        Product someProduct1 = Product.builder()
                .id(null)
                .name("Test product")
                .description("Some cool product")
                .price(new BigDecimal("99.99"))
                .stock(5)
                .build();

        Product someProduct2 = Product.builder()
                .id(null)
                .name("Test product 2")
                .description("Even better product")
                .price(new BigDecimal("109.99"))
                .stock(2)
                .build();

        // when
        Product savedProduct1 = productRepository.saveProduct(someProduct1);
        Product savedProduct2 = productRepository.saveProduct(someProduct2);

        // then
        Optional<Product> foundById = productRepository.findById(savedProduct2.getId());
        assertThat(foundById).isPresent();
        assertThat(foundById.get().getName()).isEqualTo("Test product 2");

        List<Product> foundByName = productRepository.findByName("product");
        assertThat(foundByName).hasSize(2);
        assertThat(foundByName.get(0).getDescription()).isEqualTo("Some cool product");
    }

    @Test
    void shouldFindAllProducts() {
        // given
        Product someProduct1 = Product.builder()
                .id(null)
                .name("Test product")
                .description("Some cool product")
                .price(new BigDecimal("99.99"))
                .stock(5)
                .build();

        Product someProduct2 = Product.builder()
                .id(null)
                .name("Test product 2")
                .description("Even better product")
                .price(new BigDecimal("109.99"))
                .stock(2)
                .build();

        Product savedProduct1 = productRepository.saveProduct(someProduct1);
        Product savedProduct2 = productRepository.saveProduct(someProduct2);

        // when
        List<Product> allProducts = productRepository.findAll();

        // then
        assertThat(allProducts).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product originalProduct = Product.builder()
                .id(null)
                .name("Old Name")
                .description("Old Description")
                .price(new BigDecimal("30"))
                .stock(3)
                .build();

        Product savedProduct = productRepository.saveProduct(originalProduct);

        Product updatedProduct = Product.builder()
                .id(null)
                .name("New Name")
                .description("New Description")
                .price(new BigDecimal("50"))
                .stock(2)
                .build();

        // when
        Product result = productRepository.updateProduct(savedProduct.getId(), updatedProduct);

        // then
        assertThat(result.getId()).isEqualTo(savedProduct.getId());
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void shouldDeleteProductById() {
        // given
        Product originalProduct = Product.builder()
                .id(null)
                .name("Old Name")
                .description("Old Description")
                .price(new BigDecimal("30"))
                .stock(3)
                .build();

        Product savedProduct = productRepository.saveProduct(originalProduct);

        // when
        productRepository.deleteById(savedProduct.getId());

        // then
        Optional<Product> result = productRepository.findById(savedProduct.getId());
        assertThat(result).isEmpty();
    }
}
