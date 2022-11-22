package com.programmers.voucher.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {
    private final UUID voucherID;
    private final long amount;
    private final long MIN_VOUCHER_AMOUNT = 0;
    private final long MAX_VOUCHER_AMOUNT = 10000;

    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    public FixedAmountVoucher(UUID voucherID, long amount) {
        if (amount <= MIN_VOUCHER_AMOUNT || amount > MAX_VOUCHER_AMOUNT) {
            throw new IllegalArgumentException("잘못된 범위의 할인 금액입니다.");
        }
        this.voucherID = voucherID;
        this.amount = amount;
        logger.info("FixedAmountVoucher 가 생성되었습니다.");
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount <= 0) ? 0 : discountedAmount;
    }


    @Override
    public String toString() {
        return "Voucher ID -> " + voucherID + " Voucher Type -> FixedAmount" + " Discount -> " + amount;
    }
}
