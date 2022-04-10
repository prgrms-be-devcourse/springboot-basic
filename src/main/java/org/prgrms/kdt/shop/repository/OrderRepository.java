package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Order;

public interface OrderRepository {
    public Order insert(Order order);
}
