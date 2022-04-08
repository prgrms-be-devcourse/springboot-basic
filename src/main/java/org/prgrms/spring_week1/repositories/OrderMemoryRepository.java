package org.prgrms.spring_week1.repositories;

import org.prgrms.spring_week1.models.Order;
import org.prgrms.spring_week1.models.Voucher;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderMemoryRepository implements OrderRepository{
    ConcurrentHashMap<UUID, Order> orderHashMap = new ConcurrentHashMap<>();


    @Override
    public void insert(Order order) {
        orderHashMap.put(order.getOrderId(), order);
    }
}
