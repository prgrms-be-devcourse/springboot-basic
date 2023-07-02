package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.FIXED_AMOUNT_MIN;
import static com.programmers.voucher.domain.voucher.util.VoucherErrorMessages.INVALID_FIXED_AMOUNT;

public class FixedAmountVoucher extends Voucher {
    private static final Logger LOG = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        super(voucherId);
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(long amount) {
        if (amount <= FIXED_AMOUNT_MIN) {
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_FIXED_AMOUNT, amount);
            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    @Override
    public VoucherDto toDto() {
        return new VoucherDto(super.voucherId, VoucherType.FIXED_AMOUNT, amount);
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - amount;
    }

    @Override
    public String fullInfoString() {
        return "VoucherID: " + voucherId + ", discount: " + amount + "$";
    }

    @Override
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
