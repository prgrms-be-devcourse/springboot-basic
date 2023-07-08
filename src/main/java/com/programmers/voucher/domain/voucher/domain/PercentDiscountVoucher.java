package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.UUID;

import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.PERCENT_DISCOUNT_MAX;
import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.PERCENT_DISCOUNT_MIN;
import static com.programmers.voucher.domain.voucher.util.VoucherErrorMessages.INVALID_PERCENT_DISCOUNT;

public class PercentDiscountVoucher extends Voucher {
    private static final Logger LOG = LoggerFactory.getLogger(PercentDiscountVoucher.class);

    private final long percent;

    public PercentDiscountVoucher(UUID voucherId, long percent) {
        super(voucherId);
        validatePercent(percent);
        this.percent = percent;
    }

    private void validatePercent(long percent) {
        if (noneMatchPercentDiscount(percent)) {
            String errorMessage = MessageFormat.format(INVALID_PERCENT_DISCOUNT, percent);

            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private boolean noneMatchPercentDiscount(long percent) {
        return percent <= PERCENT_DISCOUNT_MIN || percent >= PERCENT_DISCOUNT_MAX;
    }

    @Override
    public long totalAmount(long beforeAmount) {
        return beforeAmount - beforeAmount * percent / 100;
    }

    @Override
    public VoucherDto toDto() {
        return new VoucherDto(super.voucherId, VoucherType.PERCENT, percent);
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
