package org.prgrms.kdt.entity;

import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.configure.Voucher;

import java.util.List;
import java.util.UUID;

@Getter
public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;
    private Voucher voucher;

    //생성자 생성


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

    //total 계산
    public long totalAmount(){
        // 순회하면서 모든 제품의 가격값을 합한다.
        var beforeDiscount = orderItems.stream()
                .map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        if(voucher != null) return voucher.discount(beforeDiscount);
        return beforeDiscount;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void applyVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}
