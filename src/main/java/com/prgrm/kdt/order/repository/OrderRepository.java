package com.prgrm.kdt.order.repository;

import com.prgrm.kdt.order.domain.Order;

public interface OrderRepository {
    void insert(Order order);
}
