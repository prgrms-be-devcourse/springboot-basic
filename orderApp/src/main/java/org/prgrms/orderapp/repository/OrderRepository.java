package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.Order;

public interface OrderRepository {
    void save(Order order);
}
