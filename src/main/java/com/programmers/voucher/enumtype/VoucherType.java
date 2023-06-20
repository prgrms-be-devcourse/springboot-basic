package com.programmers.voucher.enumtype;

import com.programmers.voucher.domain.FixedAmountVoucher;
import com.programmers.voucher.domain.PercentDiscountVoucher;
import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.request.VoucherCreateRequest;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT("fixed", FixedAmountVoucher::new),
    PERCENT("percent", PercentDiscountVoucher::new);

    public final String type;
    public final BiFunction<UUID, VoucherCreateRequest, Voucher> function;

    VoucherType(String value, BiFunction<UUID, VoucherCreateRequest, Voucher> function) {
        this.type = value;
        this.function = function;
    }

    public static VoucherType getValue(String voucherType) {
        return Arrays.stream(values())
                .filter(t -> Objects.equals(t.type, voucherType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 바우처 타입입니다."));
    }

    public Voucher createVoucher(UUID voucherId, VoucherCreateRequest request) {
        return function.apply(voucherId, request);
    }
}
