package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
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
        if (noneMatchFixedAmount(amount)) {
            String errorMessage = MessageFormat.format(INVALID_FIXED_AMOUNT, amount);

            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean noneMatchFixedAmount(long amount) {
        return amount <= FIXED_AMOUNT_MIN;
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
    public String toString() {
        return "FixedAmountVoucher{" +
                "voucherId=" + voucherId +
                ", amount=" + amount +
                '}';
    }
}
