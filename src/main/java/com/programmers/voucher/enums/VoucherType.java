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
    FIXED("fixed", (a, b) -> new FixedAmountVoucher(a, b)),
    PERCENT("percent", (a, b) -> new PercentDiscountVoucher(a, b));

    private final String type;
    private final BiFunction<UUID, VoucherCreationRequest, Voucher> biFunction;

    VoucherType(String type, BiFunction<UUID, VoucherCreationRequest, Voucher> biFunction) {
        this.type = type;
        this.biFunction = biFunction;
    }

    public static VoucherType getVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(VoucherType -> Objects.equals(VoucherType.type, type))
                .findAny()
                .orElseThrow(() -> new VoucherException(VoucherErrorCode.NOT_SUPPORTED_TYPE));
    }

    public Voucher createVoucher(VoucherCreationRequest voucherCreationRequest) {
        return biFunction.apply(UUID.randomUUID(), voucherCreationRequest);
    }
}
