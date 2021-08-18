package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Order;

public interface OrderRepository {
    void save(Order order);
}
