package org.prgms.order.order.repository;

import org.prgms.order.order.entity.Order;

public interface OrderRepository {
    Order insert(Order order);
}
