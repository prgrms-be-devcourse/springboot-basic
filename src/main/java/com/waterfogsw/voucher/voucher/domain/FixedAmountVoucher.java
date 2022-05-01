package com.waterfogsw.voucher.voucher.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.LocalDateTime;

@JsonTypeName("FIXED_AMOUNT")
public class FixedAmountVoucher extends Voucher {

    private final int amount;

    public FixedAmountVoucher(int amount) {
        this(null, amount);
    }

    public FixedAmountVoucher(Long id, int amount) {
        super(id, VoucherType.FIXED_AMOUNT);
        validate(amount);
        this.amount = amount;
    }

    public FixedAmountVoucher(Long id, int amount, LocalDateTime createdAt) {
        super(id, VoucherType.FIXED_AMOUNT, createdAt);
        validate(amount);
        this.amount = amount;
    }

    @JsonCreator
    public FixedAmountVoucher(@JsonProperty("id") Long id,
                              @JsonProperty("type") VoucherType type,
                              @JsonProperty("value") int amount,
                              @JsonProperty("createdAt") LocalDateTime createdAt) {
        super(id, type, createdAt);
        validate(amount);
        this.amount = amount;
    }

    @Override
    public int getValue() {
        return amount;
    }

    @Override
    public int discount(int before) {
        return Math.max(before - amount, 0);
    }

    @Override
    protected void validate(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
