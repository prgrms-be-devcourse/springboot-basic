package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.time.LocalDate;
import java.util.UUID;


public abstract class Voucher {

    protected UUID voucherId;
    protected long discount;
    protected VoucherType voucherType;
    protected UseStatusType useStatusType;
    protected LocalDate createdAt;

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscount() {
        return discount;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public UseStatusType getUseStatusType() {
        return useStatusType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }


    public void updateVoucherInfo(VoucherUpdateRequest voucherUpdateRequest) {
        validateVoucherInfo(voucherUpdateRequest.getDiscount());
        this.discount = voucherUpdateRequest.getDiscount();
        this.useStatusType = UseStatusType.of(voucherUpdateRequest.getUseStatusType());
    }

    public abstract void validateVoucherInfo(long discount);
}
