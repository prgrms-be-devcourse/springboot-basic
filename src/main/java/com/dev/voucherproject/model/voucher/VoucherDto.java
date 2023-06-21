package com.dev.voucherproject.model.voucher;

import java.util.UUID;

public class VoucherDto {
    private final VoucherPolicy voucherPolicy;
    private final UUID uuid;
    private final long discountNumber;

    private VoucherDto(VoucherPolicy voucherPolicy, UUID uuid, long discountNumber) {
        this.voucherPolicy = voucherPolicy;
        this.uuid = uuid;
        this.discountNumber = discountNumber;
    }

    public static VoucherDto fromVoucher(VoucherPolicy voucherPolicy, Voucher voucher) {
        return new VoucherDto(voucherPolicy, voucher.getVoucherId(), voucher.getDiscountNumber());
    }

    public VoucherPolicy getVoucherPolicy() {
        return voucherPolicy;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getDiscountNumber() {
        return discountNumber;
    }
}
