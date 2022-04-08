package org.prgrms.deukyun.voucherapp.order.entity;

/**
 * 주문 상태
 */
public enum OrderStatus {

    ACCEPTED,               //주문 수락 상태
    PAYMENT_REQUIRED,       //구매 필요 상태
    PAYMENT_CONFIRMED,      //구매 확인 상태
    PAYMENT_REJECTED,       //구매 거절 상태
    READY_FOR_DELIVERY,     //배송 대기 상태
    SHIPPED,                //배송 완료 상태
    SETTLED,                //주문 완료 상태
    CANCELLED               //주문 취소 상태
}
