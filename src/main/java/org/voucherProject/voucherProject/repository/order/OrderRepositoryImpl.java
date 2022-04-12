package org.voucherProject.voucherProject.repository.order;

import org.springframework.stereotype.Repository;
import org.voucherProject.voucherProject.entity.order.Order;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return Optional.ofNullable(storage.get(orderId));
    }
}
