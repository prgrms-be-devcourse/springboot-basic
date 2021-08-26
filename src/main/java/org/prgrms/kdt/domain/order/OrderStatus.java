package org.prgrms.kdt.domain.order;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELED
}
