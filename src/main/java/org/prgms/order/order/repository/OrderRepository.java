package org.prgms.order.order.repository;

import org.prgms.order.order.model.Order;

public interface OrderRepository {
    Order insert(Order order);
}
