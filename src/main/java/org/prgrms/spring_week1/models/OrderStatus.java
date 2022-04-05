package org.prgrms.spring_week1.models;

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
