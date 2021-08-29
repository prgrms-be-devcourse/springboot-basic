package org.prgrms.dev.order.repository;

import org.prgrms.dev.order.domain.Order;

public interface OrderRepository {
    Order create(Order order);
}
