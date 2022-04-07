package org.prgrms.deukyun.voucherapp.entity;

import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Voucher voucher;
    private OrderStatus orderStatus;

    public Order(UUID id, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = voucher;
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long totalAmount(){
         Long beforeDiscount = orderItems.stream().map(oi-> oi.getProductPrice() * oi.getQuantity())
                .reduce(0L, Long::sum);
         return voucher.discount(beforeDiscount);
    }
}
