package org.prgrms.kdt.repository.order;


import org.prgrms.kdt.model.order.Order;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemOrderRepository implements OrderRepository {

    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(Order order) {
        storage.put(order.getOrderId(), order);
        return order;
    }
}