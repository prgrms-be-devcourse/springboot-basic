package org.prgrms.kdt.order;

import java.util.List;
import java.util.UUID;
import org.prgrms.kdt.voucher.Voucher;

/**
 * Created by yhh1056
 * Date: 2021/08/17 Time: 9:11 오후
 */
public class Order {

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
                .map(v -> v.productPrice() * v.quantity())
                .reduce(0L, Long::sum);
        if (voucher != null) {
            return voucher.discount(beforeDiscount);
        }
        return beforeDiscount;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void applyVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
