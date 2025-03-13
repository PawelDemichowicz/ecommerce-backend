package com.ecommerce.database.repository;

import com.ecommerce.business.domain.Order;
import com.ecommerce.database.entity.OrderEntity;
import com.ecommerce.database.entity.mapper.OrderEntityMapper;
import com.ecommerce.database.entity.mapper.OrderItemEntityMapper;
import com.ecommerce.database.repository.jpa.OrderItemJpaRepository;
import com.ecommerce.database.repository.jpa.OrderJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public Optional<Order> findById(Integer orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderEntityMapper::mapFromEntity);
    }

    public List<Order> findByUser(Integer userId) {
        return orderJpaRepository.findByUserId(userId).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    public Order issueOrder(Order order) {
        OrderEntity orderToSave = orderEntityMapper.mapToEntity(order);
        OrderEntity orderSaved = orderJpaRepository.saveAndFlush(orderToSave);

        order.getOrderItems().stream()
                .filter(orderItem -> Objects.isNull(orderItem.getId()))
                .map(orderItemEntityMapper::mapToEntity)
                .forEach(orderItemEntity -> {
                    orderItemEntity.setOrder(orderSaved);
                    orderItemJpaRepository.saveAndFlush(orderItemEntity);
                });
        return orderEntityMapper.mapFromEntity(orderSaved);
    }

    public void saveOrder(Order order) {
        OrderEntity orderToSave = orderEntityMapper.mapToEntity(order);
        orderJpaRepository.saveAndFlush(orderToSave);
    }
}
