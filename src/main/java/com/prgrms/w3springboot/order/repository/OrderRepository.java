package com.prgrms.w3springboot.order.repository;

import com.prgrms.w3springboot.order.Order;

public interface OrderRepository {
    Order insert(Order order);
}
