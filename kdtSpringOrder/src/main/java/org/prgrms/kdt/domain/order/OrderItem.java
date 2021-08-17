package org.prgrms.kdt.domain.order;

import java.util.UUID;

// record 란?
// 인스턴스 필드를 초기화하는 생성자가 final로 정의되어있고 각 필드의 getter 메서드를 가지고 있음. 즉,
// 1) 각 필드는 private final 필드로 정의된다.
// 2) 모든 필드를 초기화하는 RequiredAllArgument 생성자가 있다.
// 3) 해당 record 클래스는 final 클래스이라 상속할 수 없다.

//아래 코드와 동일함.
//public record OrderItem(UUID productId,
//                        long productPrice,
//                        long quantity) {
//}

public class OrderItem {
    public final UUID productId;
    public final long productPrice;
    public final long quantity;

    public OrderItem(UUID productId, long productPrice, long quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }

}