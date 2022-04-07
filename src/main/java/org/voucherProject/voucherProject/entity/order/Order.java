package org.voucherProject.voucherProject.entity.order;

import lombok.Getter;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class Order {

    private final UUID orderId;
    private final UUID userId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID userId, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderId, UUID userId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        Long beforeDiscount = orderItems.stream().map(v -> v.getItemPrice() * v.getQuantity())
                .reduce(0L, (a, b) -> (a + b));
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    /**
     * OrderItem - quantity 회복 기능 추후 추가해야함.
     */
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

}
