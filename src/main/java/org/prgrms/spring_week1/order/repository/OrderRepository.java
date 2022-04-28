package org.prgrms.spring_week1.order.repository;

import org.prgrms.spring_week1.order.model.Order;

public interface OrderRepository {

    Order insert(Order order);
}
