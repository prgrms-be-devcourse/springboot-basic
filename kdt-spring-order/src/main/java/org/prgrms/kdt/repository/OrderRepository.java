package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.order.Order;

public interface OrderRepository {
    Order insert(Order order);
}
