package org.prgrms.vouchermanager.domain.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.google.common.base.Preconditions.*;

@EqualsAndHashCode(of = "id", doNotUseGetters = true)
@Getter
public class Product {

    /* 아이디 */
    private UUID id;

    /* 이름 */
    private final String name;

    /* 가격 */
    private final long price;

    /* 재고 */
    private long stock;

    /*  상품 상태 */
    ProductStatus status;

    /* 생성 일시 */
    private LocalDateTime createdAt;

    private Product(String name, long price, long stock) {
        checkArgument(Strings.isNotBlank(name), "name은 null 혹은 공백이 될 수 없습니다.");
        checkArgument(price >= 0, "price는 양수여야 합니다.");
        checkArgument(stock >= 0, "stock은 음수가 될 수 없습니다.");

        this.name = name;
        this.price = price;
        this.stock = stock;
        setStatusByStock();
    }

    public static Product create(String name, long price, long stock) {
        return new Product(name, price, stock).withId(UUID.randomUUID()).setStatusByStock().withCreatedAt(LocalDateTime.now());
    }

    public static Product bind(UUID id, String name, long price, long stock, ProductStatus status, LocalDateTime createdAt) {
        if (stock == 0) checkState(status == ProductStatus.SOLD_OUT, "bind 데이터가 잘 못 되었습니다. stock=0일 시, status는 SOLD_OUT 이여야 함");
        if (stock > 0) checkState(status == ProductStatus.FOR_SALE, "bind 데이터가 잘 못 되었습니다. stock>0일 시, status는 FOR_SALE 이여야 함");

        return new Product(name, price, stock).withId(id).withStatus(status).withCreatedAt(createdAt);
    }

    private Product withId(UUID id) {
        checkNotNull(id, "아이디 필수");

        this.id = id;

        return this;
    }

    private Product withCreatedAt(LocalDateTime createdAt) {
        checkNotNull(createdAt, "생성 일시 필수");

        this.createdAt = createdAt.truncatedTo(ChronoUnit.MILLIS);

        return this;
    }

    private Product withStatus(ProductStatus status) {
        checkNotNull(status, "상품 상태 필수");

        this.status = status;

        return this;
    }

    /**
     * stock값에 따른 상품 status 설정
     */
    private Product setStatusByStock() {
        if (this.stock > 0) this.status = ProductStatus.FOR_SALE;
        if (this.stock == 0) this.status = ProductStatus.SOLD_OUT;

        return this;
    }

}
