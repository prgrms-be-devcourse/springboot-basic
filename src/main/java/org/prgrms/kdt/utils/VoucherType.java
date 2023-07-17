package org.prgrms.kdt.utils;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.factory.VoucherFactory;

import java.util.Random;
import java.util.function.Function;

public enum VoucherType {


    FIXED("FIXED", value -> VoucherFactory.createVoucher("FIXED",value)),
    PERCENT("PERCENT", value -> VoucherFactory.createVoucher("PERCENT",value));

    private final String matchString;
    private final Function<Long,Voucher> expression;

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
