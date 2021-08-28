package org.prgrms.orderApp.domain.order.model;

import org.prgrms.orderApp.domain.voucher.model.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Entity
public class Order implements OrderAppModel {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        Long beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }
    public UUID getOrderId(){
        return this.orderId;
    }

    public Optional<Voucher> getVoucher(){
        return voucher;
    }
    public OrderStatus getOrderStatus(){
        return orderStatus;
    }
    public List<OrderItem> gerOrderItems(){
        return orderItems;
    }
    public UUID getCustomerId(){
        return customerId;
    }
}
