package org.prgrms.dev.order.repository;

import org.prgrms.dev.order.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order create(Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }
}