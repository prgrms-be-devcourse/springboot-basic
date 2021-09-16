package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(Order order) {
        return storage.put(order.getOrderId(), order);
    }
}
