package org.prgrms.kdt.order.repository;

import org.prgrms.kdt.order.domain.Order;

public interface OrderRepository {
    void insert(Order order);
}
