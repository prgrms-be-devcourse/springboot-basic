package org.prgrms.deukyun.voucherapp.order.repository;

import org.prgrms.deukyun.voucherapp.order.entity.Order;

public interface OrderRepository {
    Order insert(Order order);
}
