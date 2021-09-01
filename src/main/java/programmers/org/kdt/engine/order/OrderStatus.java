package programmers.org.kdt.engine.order;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMED,
    PAYMENT_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELLED
}
