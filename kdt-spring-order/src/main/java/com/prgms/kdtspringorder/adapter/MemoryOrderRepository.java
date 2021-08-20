package com.prgms.kdtspringorder.adapter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.prgms.kdtspringorder.domain.model.order.Order;
import com.prgms.kdtspringorder.domain.model.order.OrderRepository;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }
}
