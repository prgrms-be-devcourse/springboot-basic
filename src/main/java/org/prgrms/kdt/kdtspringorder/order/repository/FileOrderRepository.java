package org.prgrms.kdt.kdtspringorder.order.repository;

import org.prgrms.kdt.kdtspringorder.order.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public class FileOrderRepository implements OrderRepository{
    @Override
    public void insert(Order order) {

    }
}
