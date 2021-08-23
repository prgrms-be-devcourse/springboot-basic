package org.programmers.kdt.order.repository;

import org.programmers.kdt.order.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> repository = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        repository.put(order.getOrderId(), order);
        return order;
    }
}
