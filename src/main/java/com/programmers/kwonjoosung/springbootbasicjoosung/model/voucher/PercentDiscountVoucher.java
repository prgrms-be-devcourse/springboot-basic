package com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final long MIN_PERCENT = 0L;
    private static final long MAX_PERCENT = 100L;
    private final UUID voucherId;
    private final long discountPercent;

    public PercentDiscountVoucher(UUID voucherId, long discountPercent) {
        valid(discountPercent);
        this.voucherId = voucherId;
        this.discountPercent = discountPercent;
        logger.debug("create Voucher voucherId = {}, discountPercent ={}", voucherId, discountPercent);
    }

    public PercentDiscountVoucher(long discountPercent) {
        this(UUID.randomUUID(), discountPercent);
    }

    private void valid(long discountAmount) {
        if (MIN_PERCENT >= discountAmount || discountAmount >= MAX_PERCENT) {
            logger.error("범위 초과 에러 입력 => {}", discountAmount);
            throw new RuntimeException("적절한 할인 범위를 넘어갔습니다.");
        }
    }

    @Override
    public VoucherType getVoucherType() {
        return VoucherType.PERCENT;
    }

    @Override
    public long getDiscount() {
        return discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "VoucherType: PercentDiscountVoucher, " +
                "voucherId: " + voucherId +
                ", discountPercent: " + discountPercent + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PercentDiscountVoucher that)) return false;
        return discountPercent == that.discountPercent && voucherId.equals(that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountPercent);
    }
}
