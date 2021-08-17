package org.prgrms.kdt.order;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:12 오후
 */
public enum OrderStatus {

    ACCEPTED,
    PAYMENT_REQUIRED,
    PAYMENT_CONFIRMED,
    PAYMENT_REJECTED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELLED,
}
