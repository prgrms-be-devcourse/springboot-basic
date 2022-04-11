package com.prgrms.kdt.springbootbasic.entity;

import com.prgrms.kdt.springbootbasic.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private UUID voucherId;
    private long discountAmount;

    public FixedAmountVoucher(UUID voucherId, long discountAmount){
        this.voucherId = voucherId;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountedMoney(long beforeDiscount) {
        long discountedMoney;

        if (beforeDiscount < discountAmount){
            logger.info(MessageFormat.format("[FixedAmountVoucher : getDiscountedMoney] discountAmount ({0}) is bigger than before Discount ({1})", discountAmount, beforeDiscount));
            discountedMoney = 0;
        }else {
            discountedMoney = beforeDiscount - discountAmount;
        }

        return discountedMoney;
    }

    @Override
    public long getDiscountAmount() {
        return discountAmount;
    }
}
