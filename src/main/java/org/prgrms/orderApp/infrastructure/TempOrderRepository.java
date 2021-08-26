package org.prgrms.orderApp.infrastructure;

import org.prgrms.orderApp.domain.order.model.Order;
import org.prgrms.orderApp.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TempOrderRepository implements OrderRepository {
    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, Order> selectAll() {

        return storage;
    }

    @Override
    public Order insert(Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<Order> selectById(UUID orderId) {
        return Optional.empty();
    }

}
