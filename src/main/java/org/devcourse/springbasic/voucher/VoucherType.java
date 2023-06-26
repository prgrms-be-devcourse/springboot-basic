package org.devcourse.springbasic.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER(1, FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER(2, PercentDiscountVoucher::new);

    private final int inputNum;
    private final BiFunction<UUID, Long, Voucher> FunctionToSelectVoucher;
    VoucherType(int inputNum, BiFunction<UUID, Long, Voucher> FunctionToSelectVoucher) {
        this.inputNum = inputNum;
        this.FunctionToSelectVoucher = FunctionToSelectVoucher;
    }

    public BiFunction<UUID, Long, Voucher> getFunctionToSelectVoucher() {
        return FunctionToSelectVoucher;
    }

    public static VoucherType findByMenuNum(int menuNum) {
        return Arrays.stream(VoucherType.values())
                .filter(VoucherType -> (VoucherType.inputNum == menuNum))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다. 메뉴 번호를 숫자로 정확히 입력해주세요."));
    }
}
