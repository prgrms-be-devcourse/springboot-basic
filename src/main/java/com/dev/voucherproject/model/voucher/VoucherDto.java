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
    
    public static VoucherDto fromEntity(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherPolicy(), voucher.getVoucherId(), voucher.getDiscountFigure());
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
