package org.prms.kdtordertest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderid;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
//    private long discountAmount;
//    private FixedAmountVoucher fixedAmountVoucher;
    private Optional<Voucher> voucher;

    private OrderStatus orderStatus=OrderStatus.ACCEPTED;


    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems) {
        this.orderid = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher = Optional.empty();
//        this.orderStatus = orderStatus;
    }

    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems, Voucher voucher) {
        this.orderid = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher=Optional.of(voucher);
    }

    public long totalAmount() {

        // orderItems stream으로 순회하면서 총액 구하기
        // record를 사용하면 get/setter를 안쓰고 바로 접근 가능
        var beforeDiscount=orderItems.stream().map(v->v.productPrice()*v.quantity())
                .reduce(0L,Long::sum);

        // Voucher가 존재하면 반환, 없으면 그대로 beforeDiscount 반환
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
    }



    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
