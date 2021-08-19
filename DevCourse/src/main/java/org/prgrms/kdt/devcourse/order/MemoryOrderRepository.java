package org.prgrms.kdt.devcourse.order;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryOrderRepository implements OrderRepository{
    private Map<UUID,Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(Order order) {
        storage.put(order.getOrderId(),order);
        return order;
    }
}
