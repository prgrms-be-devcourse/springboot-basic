package org.prgrms.kdt.shop.repository;

import org.prgrms.kdt.shop.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    public Order insert(Order order);
}
