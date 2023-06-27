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

    public static VoucherDto fromVoucher(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher) {
            return new VoucherDto(VoucherPolicy.FIXED_AMOUNT_VOUCHER, voucher.getVoucherId(), voucher.getDiscountNumber());
        }

        return new VoucherDto(VoucherPolicy.PERCENT_DISCOUNT_VOUCHER, voucher.getVoucherId(), voucher.getDiscountNumber());
    }

    @Override
    public String toString() {
        return "[%s, %d] %s".formatted(voucherPolicy.name(), discountNumber, uuid.toString());
    }
}
