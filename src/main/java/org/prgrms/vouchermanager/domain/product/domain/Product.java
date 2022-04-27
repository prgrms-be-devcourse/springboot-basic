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
    /* 생성 일시 */
    private final LocalDateTime createdAt = LocalDateTime.now();
    /*  상품 상태 */
    ProductStatus status;
    /* 재고 */
    private long stock;

    public Product(UUID id, String name, long price, long stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        setStatusByStock();
    }

    /**
     * stock값에 따른 상품 status 설정
     */
    private void setStatusByStock() {
        if (this.stock > 0) this.status = ProductStatus.FOR_SALE;
        if (this.stock == 0) this.status = ProductStatus.SOLD_OUT;
    }

}
