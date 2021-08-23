package org.programmers.kdt.order.repository;

import org.programmers.kdt.order.Order;

public interface OrderRepository {
    Order save(Order order);
}
