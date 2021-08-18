package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderid;
    private final UUID customerid;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderid, UUID customerid, List<OrderItem> orderItems) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
    }

    public Order(UUID orderid, UUID customerid, List<OrderItem> orderItems, Voucher voucher) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.orderItems = orderItems;
        this.voucher = Optional.of(voucher);
    }

    public long totalAmount() {
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void applyVoucher(Voucher voucher) {
        this.voucher = Optional.of(voucher);
    }
}
