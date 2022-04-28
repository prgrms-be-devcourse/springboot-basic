package org.prgrms.deukyun.voucherapp.domain.voucher.domain;

import lombok.Getter;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 바우처
 */
@Getter
public abstract class Voucher {

    /**
     * 아이디
     */
    private final UUID id;

    /**
     * 고객 아이디
     */
    private UUID customerId;

    protected Voucher(UUID id) {
        checkArgument(id != null, "id must be provided.");

        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setOwnerId(UUID customerId) {
        this.customerId = customerId;
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
