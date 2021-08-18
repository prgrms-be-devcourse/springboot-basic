package org.prgrms.kdt.devcourse;

import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;
    private Voucher voucher;


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

    public long totalAmount(){
        var beforeDiscount = orderItems.stream()
                .map(v-> v.getProductPrice() * v.getQuantity())
                .reduce(0L,Long::sum);

        if(voucher!=null)
            return voucher.discount(beforeDiscount);
        return beforeDiscount;
    }

}
