package org.prgrms.deukyun.voucherapp.domain.voucher.entity;

import java.util.UUID;

/**
 * 바우처
 */
public abstract class Voucher {

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * 바우처의 할인 로직 적용
     *
     * @param beforeDiscountPrice - 할인 전 가격
     * @return 할인된 가격
     */
    abstract public long discount(long beforeDiscountPrice);

    abstract public String toDisplayString();
}
