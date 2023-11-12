package com.zerozae.voucher.domain.voucher;

import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


public abstract class Voucher {

    protected UUID voucherId;
    protected long discount;
    protected VoucherType voucherType;
    protected UseStatusType useStatusType;
    protected LocalDateTime createdAt;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void updateVoucherInfo(VoucherUpdateRequest voucherUpdateRequest) {
        validateVoucherInfo(voucherUpdateRequest.discount());
        this.discount = voucherUpdateRequest.discount();
        this.useStatusType = UseStatusType.of(voucherUpdateRequest.useStatusType());
    }

    public abstract void validateVoucherInfo(long discount);
}
