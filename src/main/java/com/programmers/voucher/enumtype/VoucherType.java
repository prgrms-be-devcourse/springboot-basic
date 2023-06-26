package com.programmers.voucher.enumtype;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;
import com.programmers.voucher.strategy.FixedAmountValidationStrategy;
import com.programmers.voucher.strategy.PercentValidationStrategy;
import com.programmers.voucher.strategy.VoucherValidationStrategy;
import com.programmers.voucher.util.VoucherErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT("fixed",
            new FixedAmountValidationStrategy(),
            (voucherId, request) -> new FixedAmountVoucher(voucherId, request.getAmount())
    ),
    PERCENT("percent",
            new PercentValidationStrategy(),
            (voucherId, request) -> new PercentDiscountVoucher(voucherId, request.getAmount())
    );

    private static final Logger LOG = LoggerFactory.getLogger(VoucherType.class);

    private final String type;
    private final VoucherValidationStrategy voucherValidator;
    private final BiFunction<UUID, VoucherCreateRequest, Voucher> createInstance;

    VoucherType(String value,
                VoucherValidationStrategy voucherValidator,
                BiFunction<UUID, VoucherCreateRequest, Voucher> createInstance) {
        this.type = value;
        this.createInstance = createInstance;
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

    public void validateAmount(int amount) {
        voucherValidator.validateAmount(amount);
    }

    public Voucher createVoucher(UUID voucherId, VoucherCreateRequest request) {
        return createInstance.apply(voucherId, request);
    }
}
