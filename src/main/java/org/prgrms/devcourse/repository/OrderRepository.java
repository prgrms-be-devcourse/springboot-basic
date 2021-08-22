package org.prgrms.devcourse.repository;

import org.prgrms.devcourse.domain.Order;

public interface OrderRepository {
    Order insert(Order order);
}
