package org.prgrms.kdt.repository.order;

import org.prgrms.kdt.domain.order.Order;

public interface OrderRepository {

    Order insert(Order order);

}