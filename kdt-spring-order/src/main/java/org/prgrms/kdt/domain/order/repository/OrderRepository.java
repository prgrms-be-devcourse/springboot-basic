package org.prgrms.kdt.domain.order.repository;

import org.prgrms.kdt.domain.order.domain.Order;

public interface OrderRepository {
    void insert(Order order);
}