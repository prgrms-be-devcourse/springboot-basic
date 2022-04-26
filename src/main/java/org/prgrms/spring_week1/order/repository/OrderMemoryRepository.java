package org.prgrms.spring_week1.order.repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.prgrms.spring_week1.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderMemoryRepository implements OrderRepository {

    ConcurrentHashMap<UUID, Order> orderHashMap = new ConcurrentHashMap<>();


    @Override
    public void insert(Order order) {
        orderHashMap.put(order.getOrderId(), order);
    }
}
