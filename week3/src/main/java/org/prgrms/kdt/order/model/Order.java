package org.prgrms.kdt.order.model;

import org.prgrms.kdt.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItemList;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItemList, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItemList = orderItemList;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount(){
        var beforeDiscount = orderItemList.stream()
                .map(v-> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }
}

