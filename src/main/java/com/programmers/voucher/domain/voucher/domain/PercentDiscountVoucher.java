package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.dto.VoucherDto;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        if (percent <= PERCENT_DISCOUNT_MIN || percent >= PERCENT_DISCOUNT_MAX) {
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_PERCENT_DISCOUNT, percent);
            LOG.warn(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
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
    public String fullInfoString() {
        return "VoucherID: " + voucherId + ", discount: " + percent + "%";
    }

    @Override
    public String toString() {
        return "PercentDiscountVoucher{" +
                "voucherId=" + voucherId +
                ", percent=" + percent +
                '}';
    }
}
