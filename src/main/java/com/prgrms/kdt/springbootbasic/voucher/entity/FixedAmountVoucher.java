package com.prgrms.kdt.springbootbasic.voucher.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class FixedAmountVoucher extends Voucher{
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);


    public FixedAmountVoucher(UUID voucherId, long amount){
        this(voucherId,amount,LocalDateTime.now());
    }

    public FixedAmountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        super(voucherId, createdAt.truncatedTo(ChronoUnit.MILLIS));
        if (amount<0){
            this.amount = 0;
        }else {
            this.amount = amount;
        }
        this.voucherType = "Fixed";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountedMoney(long beforeDiscount) {
        long discountedMoney;

        if (beforeDiscount < amount){
            logger.info(MessageFormat.format("[FixedAmountVoucher : getDiscountedMoney] discountAmount ({0}) is bigger than before Discount ({1})", amount, beforeDiscount));
            discountedMoney = 0;
        }else {
            discountedMoney = beforeDiscount - amount;
        }

        return discountedMoney;
    }

    @Override
    public long getDiscountAmount() {
        return amount;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getVoucherType() {
        return voucherType;
    }

    @Override
    public long setAmount(long amount) {
        if (amount<0){
            this.amount = 0;
        }else {
            this.amount = amount;
        }

        return this.amount;
    }
}
