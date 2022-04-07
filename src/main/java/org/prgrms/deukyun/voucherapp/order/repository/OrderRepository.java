package org.prgrms.deukyun.voucherapp.order.repository;

import org.prgrms.deukyun.voucherapp.order.entity.Order;

/**
 * 주문 리포지토리
 */
//TODO findAll 메서드 추가, 테스트 코드
public interface OrderRepository {

    Order insert(Order order);
}
