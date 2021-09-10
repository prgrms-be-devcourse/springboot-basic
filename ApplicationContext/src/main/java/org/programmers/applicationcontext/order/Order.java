package org.programmers.applicationcontext.order;

import org.programmers.applicationcontext.voucher.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private Optional<Voucher> voucher; // 바우처 정보가 없지만 주문이 생길 수도 있으므로 Optional로 받는다
    private OrderStatus orderStatus = OrderStatus.ACCEPTED; // 항상 처음에 주문은 무조건 받겠다

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
        // this.voucher = voucher; Optional로 묶기 전의 코드
    }

    //총 청구된 비용을 계산하는 메소드
    public long totalAmount(){
        var beforeDiscount = orderItems.stream()
        .map(v -> v.getProductPrice()*v.getQuantity())
                .reduce(0L, Long::sum);
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);

        /* 아래의 if 조건문이 위의 아주 간단한 람다식으로 변모됨
        * if(voucher.isPresent()){
            return voucher.get().discount(beforeDiscount);
        }else{
            return beforeDiscount;
        }*/
    }

}
