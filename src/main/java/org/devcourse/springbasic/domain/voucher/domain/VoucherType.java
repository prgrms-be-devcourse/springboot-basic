package org.devcourse.springbasic.domain.voucher.domain;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER(1, FixedAmountVoucher::new, FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER(2, PercentDiscountVoucher::new, PercentDiscountVoucher::new);

    private final int inputNum;
    private final Supplier<Voucher> voucherSupplier;
    private final BiFunction<UUID, Long, Voucher> voucherBiFunction;

    VoucherType(int inputNum, Supplier<Voucher> voucherSupplier, BiFunction<UUID, Long, Voucher> voucherBiFunction) {
        this.inputNum = inputNum;
        this.voucherSupplier = voucherSupplier;
        this.voucherBiFunction = voucherBiFunction;
    }
    public Supplier<Voucher> getVoucherSupplier() {
        return voucherSupplier;
    }
    public BiFunction<UUID, Long, Voucher> getVoucherBiFunction() {
        return voucherBiFunction;
    }


    public static VoucherType findByMenuNum(int menuNum) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> (voucherType.inputNum == menuNum))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다. 메뉴 번호를 숫자로 정확히 입력해주세요."));
    }

}
