package org.prgrms.deukyun.voucherapp.domain.order.repository;

import org.prgrms.deukyun.voucherapp.domain.common.repository.Repository;
import org.prgrms.deukyun.voucherapp.domain.order.entity.Order;

import java.util.UUID;

/**
 * 주문 리포지토리
 */
public interface OrderRepository extends Repository<Order, UUID> {
}
