package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.util.UUID;


public abstract class Voucher {

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

    public void updateVoucherInfo(VoucherUpdateRequest voucherUpdateRequest){
        validateVoucherInfo(voucherUpdateRequest.getDiscount());
        this.discount = voucherUpdateRequest.getDiscount();
        this.useStatusType = voucherUpdateRequest.getUseStatusType();
    }

    public abstract void validateVoucherInfo(long discount);
}
