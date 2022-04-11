package com.programmers.springbootbasic.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher, Serializable {

    private final UUID voucherId;
    private final Double discountPercent;
    private final Timestamp timestamp;

    public PercentDiscountVoucher(UUID voucherId, Double discountPercent) {
        this.voucherId = voucherId;

        if (discountPercent <= 0.0 || discountPercent > 100.0)
            throw new IllegalArgumentException("유효한 값이 아닙니다.");

        this.discountPercent = discountPercent;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public Long discount(Long beforeDiscount) {
        return (long)(beforeDiscount * ((100 - discountPercent) / 100));
    }

    @Override
    public String toString() {
        return "할인권 ID: " + voucherId +
                ", 할인율: " +  discountPercent + "%" +
                ", 생성된 시간: " + timestamp;
    }

}
