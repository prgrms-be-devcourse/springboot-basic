package org.prgrms.vouchermanager.domain.product.domain;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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

    /*  상품 상태 */
    ProductStatus status;

    /* 생성 일시 */
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Product(UUID id, String name, long price, long stock) {
        checkNotNull(id);
        checkArgument(Strings.isNotBlank(name), "name은 null 혹은 공백이 될 수 없습니다.");
        checkArgument(price >= 0, "price는 양수여야 합니다.");
        checkArgument(stock >= 0, "stock은 음수가 될 수 없습니다.");

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
