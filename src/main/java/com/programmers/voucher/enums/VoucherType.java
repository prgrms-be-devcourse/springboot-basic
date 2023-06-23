package com.programmers.voucher.enums;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.exception.VoucherErrorCode;
import com.programmers.voucher.exception.VoucherException;
import com.programmers.voucher.request.VoucherCreationRequest;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED("fixed", (a, b) -> new FixedAmountVoucher(a, b), 1000),
    PERCENT("percent", (a, b) -> new PercentDiscountVoucher(a, b), 100);

    public static final int NO_COUNT = 0;
    private final String type;
    private final BiFunction<UUID, Long, Voucher> biFunction;
    private final Integer maximumDiscountAmount;

    VoucherType(String type, BiFunction<UUID, Long, Voucher> biFunction, Integer maximumDiscountAmount) {
        this.type = type;
        this.biFunction = biFunction;
        this.maximumDiscountAmount = maximumDiscountAmount;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.type, type))
                .findAny()
                .orElseThrow(() -> new VoucherException(VoucherErrorCode.NOT_SUPPORTED_TYPE));
    }

    public Voucher createVoucher(VoucherCreationRequest voucherCreationRequest) {
        validateOverDiscountAmount(voucherCreationRequest);
        return biFunction.apply(UUID.randomUUID(), voucherCreationRequest.getAmount());
    }

    private static void validateOverDiscountAmount(VoucherCreationRequest voucherCreationRequest) {
        Arrays.stream(VoucherType.values())
                .filter(voucherType -> Objects.equals(voucherType.type, voucherCreationRequest.getType())
                        && voucherType.maximumDiscountAmount > voucherCreationRequest.getAmount())
                .findAny()
                .orElseThrow(() -> new VoucherException(VoucherErrorCode.EXCEED_MAXIMUM_DISCOUNT_AMOUNT));
    }
}
