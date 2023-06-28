package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.pattern.factory.FixedAmountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.PercentDiscountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.VoucherFactory;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static com.programmers.voucher.domain.voucher.util.VoucherErrorMessages.INVALID_VOUCHER_TYPE;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
            new FixedAmountVoucherFactory()),
    PERCENT("percent",
            new PercentDiscountVoucherFactory());

    private static final Logger LOG = LoggerFactory.getLogger(VoucherType.class);

    private final String type;
    private final VoucherFactory voucherFactory;

    VoucherType(String value,
                VoucherFactory voucherFactory) {
        this.type = value;
        this.voucherFactory = voucherFactory;
    }

    public static VoucherType getValue(String voucherType) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, voucherType))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_VOUCHER_TYPE, voucherType);

                    LOG.warn(errorMessage);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public Voucher createVoucher(UUID voucherId, long amount) {
        return voucherFactory.publishVoucher(voucherId, amount);
    }

    public String getType() {
        return type;
    }
}
