package org.programmers.applicationcontext.order;

import org.programmers.applicationcontext.VoucherType;

import java.util.Arrays;

public enum OrderStatus {
    ACCEPTED("accepted"),
    PAYMENT_REQUIRED("required"),
    PAYMENT_CONFIRMED("confirmed"),
    PAYMENT_REJECTED("rejected"),
    READY_FOR_DELIVERY("delivery"),
    SHIPPED("shipped"),
    SETTLED("settled"),
    CANCLLED("canclled");

    private final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static OrderStatus of(String userOrderStatus) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> userOrderStatus.equals(orderStatus.orderStatus))
                .findFirst()
                .orElse(null);
    }
}
