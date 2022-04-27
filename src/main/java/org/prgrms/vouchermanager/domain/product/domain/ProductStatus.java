package org.prgrms.vouchermanager.domain.product.domain;

public enum ProductStatus {
    /* 재고가 있는 경우 판매 상태, 바우처 사용 가능 */
    FOR_SALE,

    /* 재고가 없는 경우 품절 상태, 바우처 사용 불가능 */
    SOLD_OUT
}
