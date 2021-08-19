package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {

    }
}
