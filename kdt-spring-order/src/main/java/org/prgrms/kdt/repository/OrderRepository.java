package org.prgrms.kdt.repository;

import org.prgrms.kdt.order.Order;

public interface OrderRepository {
    Order insert(Order order);
}
