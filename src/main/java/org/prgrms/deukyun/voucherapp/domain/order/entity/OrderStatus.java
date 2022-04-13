package org.prgrms.deukyun.voucherapp.domain.order.entity;

/**
 * 주문 상태
 */
public enum OrderStatus {

    /**
     * OrderStatus for an Order which has been accepted by 'rp '
     */
    ACCEPTED,           //주문 승인 상태

    /**
     * OrderStatus for an Order which had done its process without being cancelled
     */
    SETTLED,            //주문 안정 상태

    /**
     * OrderStatus for an Order which had been canceled by 'User'
     */
    CANCELLED           //주문 취소 상태
}
