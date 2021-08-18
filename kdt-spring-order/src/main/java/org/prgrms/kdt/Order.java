package org.prgrms.kdt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId; //식별자
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    //private FixedAmountVoucher fixedAmountVoucher;
    //강한 결합도를 약한 결합도로 바꾸기위해서 클래스 의존성을 런타임 의존성으로 변경해 준다.
    private Optional<Voucher> voucher;
    private OrderStatus orderStatus = OrderStatus.ACCECPTED;
    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems /* ,long discountAmount*/, Voucher voucher) {
        this.orderId = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        //this.fixedAmountVoucher = new FixedAmountVoucher(voucherId, discountAmount);
        this.voucher = Optional.of(voucher);
    }
    public Order(UUID orderid, UUID customerId, List<OrderItem> orderItems /* ,long discountAmount*/) {
        this.orderId = orderid;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.voucher=Optional.empty();
    }
    public long totalAmount(){
        var beforeDiscount = orderItems.stream().map(v->v.getProductPrice()* v.getQuantity())
                .reduce(0L,Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);
        //return fixedAmountVoucher.discount(beforeDiscount) ;
        //return voucher.get.discount(beforeDiscount) ;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public UUID getOrderId() {
        return orderId;
    }
}
