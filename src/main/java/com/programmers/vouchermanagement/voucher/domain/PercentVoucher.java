package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

import com.programmers.vouchermanagement.voucher.dto.GeneralVoucherDTO;

public class PercentVoucher implements Voucher {
    private static final String INVALID_DISCOUNT_INPUT_MESSAGE =
            "Input should be a number greater than 0 and smaller than 100";
    private final UUID voucherID;
    private final long discountPercent;

    public PercentVoucher(UUID voucherID, long discountPercent) {
        validateDiscountPercent(discountPercent);
        this.voucherID = voucherID;
        this.discountPercent = discountPercent;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long getDiscountValue() {
        return discountPercent;
    }

    @Override
    public long discount(long priceBeforeDiscount) {
        return (long) (priceBeforeDiscount * (1 - discountPercent / 100D));
    }

    @Override
    public GeneralVoucherDTO toVoucherDTO() {
        return new GeneralVoucherDTO(voucherID, discountPercent, VoucherType.PERCENT);
    }

    private void validateDiscountPercent(long discountPercent) {
        if (discountPercent <= 0 || discountPercent > 100) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_INPUT_MESSAGE);
        }
    }
}
