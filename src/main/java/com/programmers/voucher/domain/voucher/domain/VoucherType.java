package com.programmers.voucher.domain.voucher.domain;

import com.programmers.voucher.domain.voucher.pattern.factory.FixedAmountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.PercentDiscountVoucherFactory;
import com.programmers.voucher.domain.voucher.pattern.factory.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final Map<String, VoucherType> types =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(VoucherType::getType, Function.identity())));

    public static VoucherType getValue(String type) {
        return Optional.ofNullable(types.get(type))
                .orElseThrow(() -> {
                    String errorMessage = MessageFormat.format(INVALID_VOUCHER_TYPE, type);

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
