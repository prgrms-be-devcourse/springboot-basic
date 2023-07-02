package org.prgrms.kdt.utils;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {

    FIXED_AMOUNT_VOUCHER("FIXED_AMOUNT_VOUCHER", value->new FixedAmountVoucher(UUID.randomUUID(), value)),
    PERCENT_DISCOUNT_VOUCHER("PERCENT_DISCOUNT_VOUCHER", value->new PercentDiscountVoucher(UUID.randomUUID(), value));

    private final String matchString;
    private final Function<Long, Voucher> expression;

    VoucherType(String matchString,Function<Long, Voucher> expression) {
        this.matchString = matchString;
        this.expression = expression;
    }

    public static VoucherType of(String input) {
        return java.util.Arrays.stream(values())
                .filter(voucherType -> voucherType.matchString.equals(input))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("입력이 잘못되었습니다."));
    }

    public Voucher makeVoucher(long discount){
        return expression.apply(discount);
    }

}
