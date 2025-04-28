package com.ecommerce.integration.repository;

import com.ecommerce.business.domain.Order;
import com.ecommerce.business.domain.OrderItem;
import com.ecommerce.business.domain.User;
import com.ecommerce.database.entity.mapper.OrderEntityMapperImpl;
import com.ecommerce.database.entity.mapper.OrderItemEntityMapperImpl;
import com.ecommerce.database.entity.mapper.ProductEntityMapperImpl;
import com.ecommerce.database.entity.mapper.UserEntityMapperImpl;
import com.ecommerce.database.repository.OrderRepository;
import com.ecommerce.database.repository.ProductRepository;
import com.ecommerce.database.repository.UserRepository;
import com.ecommerce.integration.repository.configuration.AbstractJpaIT;
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
        OrderRepository.class,
        OrderEntityMapperImpl.class,
        OrderItemEntityMapperImpl.class,
        ProductRepository.class,
        ProductEntityMapperImpl.class,
        UserRepository.class,
        UserEntityMapperImpl.class
})
public class OrderRepositoryJpaIT extends AbstractJpaIT {

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Test
    void shouldFindOrderById() {
        // given
        User user = userRepository.save(someUser1());
        OrderItem orderItem = someOrderItem1();
        Order order = orderRepository.issueOrder(someOrder(user, List.of(orderItem)));

        // when
        Optional<Order> foundOrder = orderRepository.findById(order.getId());

        // then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getUser().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFound() {
        // when
        Optional<Order> foundOrder = orderRepository.findById(999);

        // then
        assertThat(foundOrder).isEmpty();
    }

    @Test
    void shouldFindByIdAndUserId() {
        // given
        User user = userRepository.save(someUser1());
        OrderItem orderItem = someOrderItem1();
        Order order = orderRepository.issueOrder(someOrder(user, List.of(orderItem)));

        // when
        Optional<Order> foundOrder = orderRepository.findByIdAndUserId(order.getId(), user.getId());

        // then
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(order.getId());
    }

    @Test
    void shouldReturnEmptyWhenOrderNotFoundByIdAndUserId() {
        // given
        User user = userRepository.save(someUser1());

        // when
        Optional<Order> foundOrder = orderRepository.findByIdAndUserId(999, user.getId());

        // then
        assertThat(foundOrder).isEmpty();
    }

    @Test
    void shouldFindByUser() {
        // given
        User user = userRepository.save(someUser1());
        OrderItem orderItem = someOrderItem1();
        orderRepository.issueOrder(someOrder(user, List.of(orderItem)));

        // when
        List<Order> foundOrder = orderRepository.findByUser(user.getId());

        // then
        assertThat(foundOrder).isNotEmpty();
        assertThat(foundOrder.get(0).getUser().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void shouldReturnEmptyWhenNoOrdersFoundForUser() {
        // given
        User user1 = userRepository.save(someUser1());
        User user2 = userRepository.save(someUser2());
        OrderItem orderItem1 = someOrderItem1();
        OrderItem orderItem2 = someOrderItem2();
        someOrder(user1, List.of(orderItem1, orderItem2));

        // when
        List<Order> foundOrders = orderRepository.findByUser(user2.getId());

        // then
        assertThat(foundOrders).isEmpty();
    }

    @Test
    void shouldFindAllOrders() {
        // given
        User user1 = userRepository.save(someUser1());
        User user2 = userRepository.save(someUser2());
        OrderItem orderItem1 = someOrderItem1();
        OrderItem orderItem2 = someOrderItem2();

        orderRepository.issueOrder(someOrder(user1, List.of(orderItem1, orderItem2)));
        orderRepository.issueOrder(someOrder(user2, List.of(orderItem1)));

        // when
        List<Order> foundOrder = orderRepository.findAll();

        // then
        assertThat(foundOrder).hasSize(6);
    }

    @Test
    void shouldIssueOrderWithOrderItems() {
        // given
        User user = userRepository.save(someUser1());
        OrderItem orderItem1 = someOrderItem1();
        OrderItem orderItem2 = someOrderItem2();
        Order order = someOrder(user, List.of(orderItem1, orderItem2));

        // when
        Order issuedOrder = orderRepository.issueOrder(order);

        // then
        Optional<Order> foundOrder = orderRepository.findById(issuedOrder.getId());
        assertThat(foundOrder).isPresent();

        Order savedOrder = foundOrder.get();
        assertThat(savedOrder.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedOrder.getOrderItems()).hasSize(2);

        assertThat(savedOrder.getOrderItems())
                .anySatisfy(item -> {
                    assertThat(item.getProductName()).isEqualTo("Product 1");
                    assertThat(item.getQuantity()).isEqualTo(3);
                })
                .anySatisfy(item -> {
                    assertThat(item.getProductName()).isEqualTo("Product 2");
                    assertThat(item.getQuantity()).isEqualTo(2);
                });
    }

    @Test
    void shouldSaveOrder() {
        // given
        User user = userRepository.save(someUser2());
        OrderItem orderItem = someOrderItem1();
        Order order = someOrder(user, List.of(orderItem));

        // when
        Order savedOrder = orderRepository.saveOrder(order);

        // then
        Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());
        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getUser().getEmail()).isEqualTo("charlie@example.com");
    }
}