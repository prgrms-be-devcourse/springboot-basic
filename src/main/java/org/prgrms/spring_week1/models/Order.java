package org.prgrms.spring_week1.models;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {

    UUID orderId;
    UUID customerId;
    private List<OrderItem> orderItemList;
    private Optional<Voucher> voucher; // 없을 수도 있음
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처 없는 경우
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList,
        Optional<Voucher> voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.voucher = voucher;
        this.orderStatus = orderStatus;
    }

    // 바우처 있는경우
    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.orderStatus = orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long totalPrice() {
        Long beforeDiscount = orderItemList
            .stream()
            .map(i -> i.getProductPrice() * i.getQuantity())
            .reduce(0L, Long::sum);

        if (voucher.isPresent()) {
            return voucher.get().discount(beforeDiscount);
        } else {
            return beforeDiscount;
        }

    }

    public UUID getOrderId() {
        return orderId;
    }
}
