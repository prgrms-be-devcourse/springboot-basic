package org.prgrms.kdt.order;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    void insert(Order order);
}
