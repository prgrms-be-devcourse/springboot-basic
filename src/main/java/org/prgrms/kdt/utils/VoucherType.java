package org.prgrms.kdt.utils;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;

import java.util.Random;
import java.util.function.Function;

public enum VoucherType {


    FIXED("FIXED", (value)->new FixedAmountVoucher(new Random().nextLong(),value)),
    PERCENT("PERCENT", (value)->new PercentDiscountVoucher(new Random().nextLong(),value));

    private final String matchString;
    private final Function<Long,Voucher> expression;

    private static class Sequence { // 정적 멤버 클래스
        private static Long SEQUENCE_ID = 0L;
    }
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
