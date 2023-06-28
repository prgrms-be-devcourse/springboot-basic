package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class VoucherDto {
    private final VoucherPolicy voucherPolicy;
    private final UUID voucherId;
    private final long discountNumber;

    private VoucherDto(VoucherPolicy voucherPolicy, UUID voucherId, long discountNumber) {
        this.voucherPolicy = voucherPolicy;
        this.voucherId = voucherId;
        this.discountNumber = discountNumber;
    }
    
    public static VoucherDto fromEntity(VoucherPolicy voucherPolicy, Voucher voucher) {
        return new VoucherDto(voucherPolicy, voucher.getVoucherId(), voucher.getDiscountNumber());
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountNumber() {
        return discountNumber;
    }
}
