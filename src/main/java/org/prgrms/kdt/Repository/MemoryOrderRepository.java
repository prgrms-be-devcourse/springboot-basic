package org.prgrms.kdt.Repository;

import org.prgrms.kdt.Model.Order;
import org.prgrms.kdt.Model.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryOrderRepository implements OrderRepository{
    private final Map<UUID, Order> storage=new ConcurrentHashMap<>();
    @Override
    public Order save(Order order) {
        storage.put(order.getOrderId(),order);
        return order;

    }
}
