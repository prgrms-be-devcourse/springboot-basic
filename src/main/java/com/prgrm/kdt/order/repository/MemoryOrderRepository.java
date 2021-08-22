package com.prgrm.kdt.order.repository;

import com.prgrm.kdt.order.domain.Order;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public void insert(Order order) {
        storage.put(order.getOrderId(), order);
    }
}
