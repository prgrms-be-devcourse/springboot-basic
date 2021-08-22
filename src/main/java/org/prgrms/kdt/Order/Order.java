package org.prgrms.kdt.Order;

import org.prgrms.kdt.Voucher.Voucher;

import java.util.List;
import java.util.UUID;


public class Order {
    //식별자는 uuid,long많이씀
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Voucher voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = voucher;
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = null;
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        if (voucher != null) return voucher.discount(beforeDiscount);
        return beforeDiscount;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void applyVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
