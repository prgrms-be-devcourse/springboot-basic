package com.prgrms.kdt.springbootbasic.voucher.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class PercentDiscountVoucher extends Voucher{

    public PercentDiscountVoucher(UUID voucherId, long amount){
        this(voucherId,amount,LocalDateTime.now());
    }

    public PercentDiscountVoucher(UUID voucherId, long amount, LocalDateTime createdAt) {
        super(voucherId, createdAt.truncatedTo(ChronoUnit.MILLIS));
        if (amount >100){
            this.amount = 100;
        }else if (amount<0){
            this.amount = 0;
        }else{
            this.amount = amount;
        }
        this.voucherType = "Percent";
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long getDiscountedMoney(long beforeDiscount) {

        return beforeDiscount * (100 - amount) / 100;
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
        if (amount >100){
            this.amount = 100;
        }else if (amount<0){
            this.amount = 0;
        }else{
            this.amount = amount;
        }

        return this.amount;
    }


}
