package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Order;

public interface OrderRepository {
    Order insert(Order order);
}
