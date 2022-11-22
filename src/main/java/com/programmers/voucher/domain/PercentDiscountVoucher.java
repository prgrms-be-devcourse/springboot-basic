package com.programmers.voucher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final UUID voucherID;
    private final long discountPercent;

    private final long MIN_PERCENT = 0;
    private final long MAX_PERCENT = 100;

    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    public PercentDiscountVoucher(UUID voucherID, long discountPercent) {
        if (discountPercent <= MIN_PERCENT || discountPercent > MAX_PERCENT) {
            throw new IllegalArgumentException("잘못된 범위의 할인율입니다.");
        }
        this.voucherID = voucherID;
        this.discountPercent = discountPercent;
        logger.info("PercentDiscountVoucher 가 생성되었습니다.");
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }


    public long discount(long beforeDiscount) {
        return beforeDiscount * (100 - discountPercent) / 100;
    }

    @Override
    public String toString() {
        return "Voucher ID -> " + voucherID + " Voucher Type -> PercentDiscount" + " Discount -> " + discountPercent;
    }
}
