package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountDiscountVoucher implements Voucher {
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountDiscountVoucher.class);
    private static final long MIN_AMOUNT = 0L;
    private static final long MAX_AMOUNT = 100_000L;
    private final UUID voucherId;
    private final long discountAmount;

    public FixedAmountDiscountVoucher(long discountAmount) {
        this(UUID.randomUUID(), discountAmount);
    }

    public FixedAmountDiscountVoucher(UUID voucherId, long discountAmount) {
        valid(discountAmount);
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
        logger.debug("create Voucher voucherId = {}, discountAmount ={}", voucherId, discountAmount);
    }

    private void valid(long discountAmount) {
        if (MIN_AMOUNT >= discountAmount || discountAmount >= MAX_AMOUNT) {
            logger.error("범위 초과 에러 입력 => {}", discountAmount);
            throw new RuntimeException("적절한 할인 범위를 넘어갔습니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.FIXED;
    }

    @Override
    public long getDiscount() {
        return discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discountAmount;
    }

    @Override
    public String toString() {
        return "VoucherType: FixedAmountDiscountVoucher, " +
                "voucherId: " + voucherId +
                ", discountPercent: " + discountAmount + "$";
    }

}
