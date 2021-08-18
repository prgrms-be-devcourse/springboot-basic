package org.prgrms.kdt.order;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

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
/*
@Getter
@Setter
public record OrderItem (UUID productId,
                         long productPrice,
                         long quantity){

}  // 자바 14 버전 부터 가능

 */
