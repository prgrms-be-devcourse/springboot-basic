package org.prgrms.kdt.order.repository;

import org.prgrms.kdt.order.Order;

public interface OrderRepository {
    Order insert(Order order);
}
