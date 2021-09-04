package org.prgrms.kdt.order.repository;

import org.prgrms.kdt.order.domain.Order;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    Order insert(Order order);
    List<Order> orders = new ArrayList<>();
}
