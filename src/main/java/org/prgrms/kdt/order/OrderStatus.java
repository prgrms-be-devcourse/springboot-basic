package org.prgrms.kdt.order;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMED,
    PAYMENT_REJECTED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELED
}
