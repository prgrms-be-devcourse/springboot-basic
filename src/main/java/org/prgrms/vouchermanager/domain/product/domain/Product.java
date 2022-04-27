package org.prgrms.vouchermanager.domain.product.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Product {

    /* 아이디 */
    private final UUID id;

    /* 이름 */
    private final String name;

    /* 가격 */
    private final long price;

    /* 재고 */
    private long stock;

    /* 생성 일시 */
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Product(UUID id, String name, long price, long stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

}
