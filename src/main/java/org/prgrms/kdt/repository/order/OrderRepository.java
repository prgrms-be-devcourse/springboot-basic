package org.prgrms.kdt.repository.order;

import org.prgrms.kdt.model.order.Order;

public interface OrderRepository {

    Order insert(Order order);
}
