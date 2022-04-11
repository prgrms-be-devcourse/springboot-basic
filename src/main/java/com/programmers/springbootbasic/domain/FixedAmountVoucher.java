package com.programmers.springbootbasic.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher, Serializable {

    public static final Long FIXED_AMOUNT_UPPER_LIMIT = 100000L;

    private final UUID voucherId;
    private final Long fixedAmount;
    private final Timestamp timestamp;

    public FixedAmountVoucher(UUID voucherId, Long fixedAmount) {
        this.voucherId = voucherId;
        this.fixedAmount = fixedAmount;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return beforeDiscount - fixedAmount;
    }

    public Long getFixedAmount() {
        return fixedAmount;
    }

    @Override
    public String toString() {
        return "할인권 ID: " + voucherId +
                ", 할인 금액: " + fixedAmount +
                ", 생성된 시간: " + timestamp;
    }

}
