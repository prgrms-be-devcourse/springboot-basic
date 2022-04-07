package org.prgrms.deukyun.voucherapp.order.repository;

import org.prgrms.deukyun.voucherapp.order.entity.Order;

/**
 * 주문 리포지토리
 */
public interface OrderRepository {
    Order insert(Order order);
}
