package com.prgrms.devkdtorder.order.domain;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMD,
    PAYMENT_REJECTED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELLED
}
