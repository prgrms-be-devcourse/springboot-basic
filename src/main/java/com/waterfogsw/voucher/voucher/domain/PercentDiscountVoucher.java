package com.waterfogsw.voucher.voucher.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;

@JsonTypeName("PERCENT_DISCOUNT")
public class PercentDiscountVoucher extends Voucher {

    private final int percent;

    public PercentDiscountVoucher(int percent) {
        this(null, percent);
    }

    public PercentDiscountVoucher(Long id, int percent) {
        super(id, VoucherType.PERCENT_DISCOUNT);
        validate(percent);
        this.percent = percent;
    }

    public PercentDiscountVoucher(Long id, int percent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, VoucherType.PERCENT_DISCOUNT, createdAt, updatedAt);
        validate(percent);
        this.percent = percent;
    }

    @JsonCreator
    public PercentDiscountVoucher(@JsonProperty("id") Long id,
                                  @JsonProperty("type") VoucherType type,
                                  @JsonProperty("value") int percent,
                                  @JsonProperty("createdAt") LocalDateTime createdAt,
                                  @JsonProperty("updatedAt") LocalDateTime updatedAt) {
        super(id, type, createdAt, updatedAt);
        validate(percent);
        this.percent = percent;
    }

    @Override
    public int getValue() {
        return percent;
    }

    @Override
    public int discount(int before) {
        int restPercent = 100 - percent;
        return before * restPercent / 100;
    }

    @Override
    protected void validate(int percent) {
        if (!(percent > 0 && percent <= 100)) {
            throw new IllegalArgumentException();
        }
    }
}
