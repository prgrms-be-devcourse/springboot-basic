package org.prgrms.deukyun.voucherapp.order.entity;

/**
 * 주문 상태
 */
public enum OrderStatus {
    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMED,
    PAYMENT_REJECTED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELLED
}
