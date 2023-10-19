package com.programmers.vouchermanagement.voucher.domain;

import java.util.UUID;

import com.programmers.vouchermanagement.voucher.dto.VoucherResponseDTO;

public class FixedAmountVoucher implements Voucher {
    private static final String INVALID_DISCOUNT_INPUT_MESSAGE =
            "Input should be a number greater than 0";
    private final UUID voucherID;
    private final long discountAmount;

    public FixedAmountVoucher(UUID voucherID, long discountAmount) {
        validateDiscountAmount(discountAmount);
        this.voucherID = voucherID;
        this.discountAmount = discountAmount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherID;
    }

    @Override
    public long discount(long priceBeforeDiscount) {
        return priceBeforeDiscount - discountAmount;
    }

    @Override
    public VoucherResponseDTO toResponseDTO() {
        return new VoucherResponseDTO(voucherID, discountAmount, VoucherType.FIXED);
    }

    private void validateDiscountAmount(long discountAmount) {
        if (discountAmount <= 0) {
            throw new IllegalArgumentException(INVALID_DISCOUNT_INPUT_MESSAGE);
        }
    }
}
