package org.prgrms.deukyun.voucherapp.order.repository;

import org.prgrms.deukyun.voucherapp.order.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 메모리 주문 리포지토리
 */
@Repository
public class MemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> storage = new ConcurrentHashMap<>();

    @Override
    public Order insert(Order order) {
        return storage.put(order.getId(), order);
    }
}
