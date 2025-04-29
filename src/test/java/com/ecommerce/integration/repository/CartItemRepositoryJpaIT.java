package com.ecommerce.integration.repository;

import com.ecommerce.business.domain.CartItem;
import com.ecommerce.business.domain.Product;
import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.mapper.CartItemEntityMapperImpl;
import com.ecommerce.database.entity.mapper.ProductEntityMapperImpl;
import com.ecommerce.database.entity.mapper.UserEntityMapperImpl;
import com.ecommerce.database.repository.CartItemRepository;
import com.ecommerce.database.repository.ProductRepository;
import com.ecommerce.database.repository.UserRepository;
import com.ecommerce.integration.configuration.AbstractJpaIT;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static com.ecommerce.util.DomainFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor(onConstructor_ = @__(@Autowired))
@Import({
        CartItemRepository.class,
        CartItemEntityMapperImpl.class,
        ProductRepository.class,
        ProductEntityMapperImpl.class,
        UserRepository.class,
        UserEntityMapperImpl.class
})
public class CartItemRepositoryJpaIT extends AbstractJpaIT {

    private CartItemRepository cartItemRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Test
    void shouldFindCartItemById() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());
        CartItem cartItem = cartItemRepository.saveCartItem(someCartItem1(user, product));

        // when
        Optional<CartItem> foundCartItem = cartItemRepository.findById(cartItem.getId());

        // then
        assertThat(foundCartItem).isPresent();
        assertThat(foundCartItem.get().getProduct().getName()).isEqualTo("Test product 1");
        assertThat(foundCartItem.get().getUser().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldReturnEmptyWhenCartItemNotFoundById() {
        // when
        Optional<CartItem> foundCartItem = cartItemRepository.findById(999);

        // then
        assertThat(foundCartItem).isEmpty();
    }

    @Test
    void shouldFindCartItemsByUserId() {
        // given
        User user = userRepository.save(someUser1());
        Product product1 = productRepository.saveProduct(someProduct1());
        Product product2 = productRepository.saveProduct(someProduct2());

        cartItemRepository.saveCartItem(someCartItem1(user, product1));
        cartItemRepository.saveCartItem(someCartItem2(user, product2));

        // when
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

        // then
        assertThat(cartItems).hasSize(2);
    }

    @Test
    void shouldReturnEmptyListWhenNoCartItemsForUser() {
        // given
        User user = userRepository.save(someUser1());

        // when
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());

        // then
        assertThat(cartItems).isEmpty();
    }

    @Test
    void shouldFindCartItemByUserAndProduct() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());
        cartItemRepository.saveCartItem(someCartItem1(user, product));

        // when
        Optional<CartItem> foundCartItem = cartItemRepository.findByUserAndProduct(user.getId(), product.getId());

        // then
        assertThat(foundCartItem).isPresent();
        assertThat(foundCartItem.get().getQuantity()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyWhenCartItemNotFoundByUserAndProduct() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());

        // when
        Optional<CartItem> foundCartItem = cartItemRepository.findByUserAndProduct(user.getId(), product.getId());

        // then
        assertThat(foundCartItem).isEmpty();
    }

    @Test
    void shouldSaveCartItem() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());
        CartItem cartItem = someCartItem1(user, product);

        // when
        CartItem savedCartItem = cartItemRepository.saveCartItem(cartItem);

        // then
        assertThat(savedCartItem.getId()).isNotNull();
        assertThat(savedCartItem.getQuantity()).isEqualTo(1);
    }

    @Test
    void shouldDeleteCartItemById() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());
        CartItem cartItem = cartItemRepository.saveCartItem(someCartItem1(user, product));

        // when
        cartItemRepository.deleteById(cartItem.getId());

        // then
        Optional<CartItem> foundCartItem = cartItemRepository.findById(cartItem.getId());
        assertThat(foundCartItem).isEmpty();
    }

    @Test
    void shouldDeleteCartItemByUserId() {
        // given
        User user = userRepository.save(someUser1());
        Product product1 = productRepository.saveProduct(someProduct1());
        Product product2 = productRepository.saveProduct(someProduct2());

        cartItemRepository.saveCartItem(someCartItem1(user, product1));
        cartItemRepository.saveCartItem(someCartItem2(user, product2));

        // when
        cartItemRepository.deleteByUserId(user.getId());

        // then
        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        assertThat(cartItems).isEmpty();
    }

    @Test
    void shouldDeleteCartItemsByProductId() {
        // given
        User user = userRepository.save(someUser1());
        Product product = productRepository.saveProduct(someProduct1());

        cartItemRepository.saveCartItem(someCartItem1(user, product));

        // when
        cartItemRepository.deleteByProductId(product.getId());

        // then
        Optional<CartItem> foundCartItem = cartItemRepository.findByUserAndProduct(user.getId(), product.getId());
        assertThat(foundCartItem).isEmpty();
    }
}