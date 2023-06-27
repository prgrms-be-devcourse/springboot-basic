package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.pattern.factory.FixedAmountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.PercentDiscountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.VoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.strategy.FixedAmountValidationStrategy;
import com.programmers.voucher.domain.voucher.pattern.strategy.PercentValidationStrategy;
import com.programmers.voucher.domain.voucher.pattern.strategy.VoucherValidationStrategy;
import com.programmers.voucher.global.util.VoucherErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
            new FixedAmountValidationStrategy(),
            new FixedAmountVoucherFactory()),
    PERCENT("percent",
            new PercentValidationStrategy(),
            new PercentDiscountVoucherFactory());

    private static final Logger LOG = LoggerFactory.getLogger(VoucherType.class);

    private final String type;
    private final VoucherValidationStrategy voucherValidator;
    private final VoucherFactory voucherFactory;

    VoucherType(String value,
                VoucherValidationStrategy voucherValidator, VoucherFactory voucherFactory) {
        this.type = value;
        this.voucherFactory = voucherFactory;
        this.voucherValidator = voucherValidator;
    }

    public static VoucherType getValue(String voucherType) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, voucherType))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = VoucherErrorMessages.INVALID_VOUCHER_TYPE + voucherType;

                    LOG.warn("Invalid voucher type: {}", errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public void validateAmount(long amount) {
        voucherValidator.validateAmount(amount);
    }

    public Voucher createVoucher(UUID voucherId, long amount) {
        return voucherFactory.publishVoucher(voucherId, amount);
    }
}
