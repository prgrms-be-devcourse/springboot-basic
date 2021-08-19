package org.prgrms.kdt.repository;

import org.prgrms.kdt.model.Order;

public interface OrderRepository {
    Order insert(Order order);
}
