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

import static com.ecommerce.util.DomainFixtures.someProduct1;
import static com.ecommerce.util.DomainFixtures.someProduct2;
import static org.assertj.core.api.Assertions.assertThat;

@Import({ProductRepository.class, ProductEntityMapperImpl.class})
public class ProductRepositoryJpaIT extends AbstractJpaIT {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProductByIdAndName() {
        // given
        Product someProduct1 = someProduct1();
        Product someProduct2 = someProduct2();

        // when
        productRepository.saveProduct(someProduct1);
        Product savedProduct2 = productRepository.saveProduct(someProduct2);

        // then
        Optional<Product> foundById = productRepository.findById(savedProduct2.getId());
        assertThat(foundById).isPresent();
        assertThat(foundById.get().getName()).isEqualTo("Test product 2");

        List<Product> foundByName = productRepository.findByName("product");
        assertThat(foundByName).hasSize(2);
        assertThat(foundByName.get(0).getDescription()).isEqualTo("Test description 1");
    }

    @Test
    void shouldFindAllProducts() {
        // given
        Product someProduct1 = someProduct1();
        Product someProduct2 = someProduct2();

        productRepository.saveProduct(someProduct1);
        productRepository.saveProduct(someProduct2);

        // when
        List<Product> allProducts = productRepository.findAll();

        // then
        assertThat(allProducts).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product originalProduct = someProduct1();

        Product savedProduct = productRepository.saveProduct(originalProduct);

        Product updatedProduct = someProduct1()
                .withName("New name")
                .withDescription("New description")
                .withPrice(new BigDecimal("50"))
                .withStock(2);

        // when
        Product result = productRepository.updateProduct(savedProduct.getId(), updatedProduct);

        // then
        assertThat(result.getId()).isEqualTo(savedProduct.getId());
        assertThat(result.getName()).isEqualTo("New name");
        assertThat(result.getPrice()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void shouldDeleteProductById() {
        // given
        Product originalProduct = someProduct1();

        Product savedProduct = productRepository.saveProduct(originalProduct);

        // when
        productRepository.deleteById(savedProduct.getId());

        // then
        Optional<Product> result = productRepository.findById(savedProduct.getId());
        assertThat(result).isEmpty();
    }
}
