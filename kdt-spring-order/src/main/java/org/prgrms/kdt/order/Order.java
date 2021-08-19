package org.prgrms.kdt.order;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// '주문'이라는 행위의 entity
public class Order {
    private final UUID orderId;
    private final UUID customId;
    // OrderItem은 상품 정보를 담는 클래스
    private final List<OrderItem> orderItem;
    private Optional<Voucher> voucher;
    // OrderStatus는 enum 클래스
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    // 바우처 없는 생성자자
    public Order(UUID orderId, UUID customId, List<OrderItem> orderItem) {
        this.orderId = orderId;
        this.customId = customId;
        this.orderItem = orderItem;
        this.voucher = Optional.empty();
    }

    // 생성자 : 주문id / 고객id / 장바구니? / 가격계산 바우처
    public Order(UUID orderId, UUID customId, List<OrderItem> orderItem, Voucher voucher) {
        this.orderId = orderId;
        this.customId = customId;
        this.orderItem = orderItem;
        this.voucher = Optional.of(voucher);
    }


    // '총 가격'을 구하는 메소드
    public long totalAmount() {
        // 1. orderItem 리스트에 있는 품목 클래스를 스트림으로 변경
        // 2. map : 클래스 -> 품목의 가격 * 수량 = Long 타입으로 변경
        // 3. reduce : 리스트(스트림)의 가격 * 수량을 모두 더하기
        var beforeDiscount = orderItem.stream()
                .map(v -> v.getProductPrice()*v.getQuantity())
                .reduce(0L, Long::sum);

        // 모두 더해진 가격에서 할인 가격을 빼준다~ (바우처가 존재할때만)
        return voucher.map(value -> value.discount(beforeDiscount)).orElse(beforeDiscount);

//        if (voucher.isPresent()){
//            return voucher.get().discount(beforeDiscount);
//        }
//        else{
//            return beforeDiscount;
//        }

    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

