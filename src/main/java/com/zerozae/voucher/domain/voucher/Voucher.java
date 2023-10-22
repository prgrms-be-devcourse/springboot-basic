package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.exception.ErrorMessage;

import java.util.UUID;


public abstract class Voucher {

    private final static long ZERO = 0;
    protected UUID voucherId;
    protected long discount;
    protected VoucherType voucherType;
    protected UseStatusType useStatusType;

    public UUID getVoucherId(){
        return voucherId;
    }

    public long getDiscount(){
        return discount;
    }

    public VoucherType getVoucherType(){
        return voucherType;
    }

    public UseStatusType getUseStatusType(){
        return useStatusType;
    }

    public abstract void validateVoucherInfo(long discount);
}
