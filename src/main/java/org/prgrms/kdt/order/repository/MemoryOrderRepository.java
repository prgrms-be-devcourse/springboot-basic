package org.prgrms.kdt.order.repository;

import org.prgrms.kdt.order.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"memory", "dev"})
public class MemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(final Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }
}