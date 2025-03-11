package com.ecommerce.database.repository;

import com.ecommerce.business.domain.Order;
import com.ecommerce.database.entity.OrderEntity;
import com.ecommerce.database.entity.mapper.OrderEntityMapper;
import com.ecommerce.database.repository.jpa.OrderJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderEntityMapper orderEntityMapper;

    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .map(orderEntityMapper::mapFromEntity);
    }

    public List<Order> findByUser(Long userId) {
        return orderJpaRepository.findByUserId(userId).stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream()
                .map(orderEntityMapper::mapFromEntity)
                .toList();
    }

    public void saveOrder(Order order) {
        OrderEntity orderToSave = orderEntityMapper.mapToEntity(order);
        orderJpaRepository.save(orderToSave);
    }
}
