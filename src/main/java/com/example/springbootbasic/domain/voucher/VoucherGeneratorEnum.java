package com.example.springbootbasic.domain.voucher;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum VoucherGeneratorEnum {

    FIXED_AMOUNT_VOUCHER("fixed", FixedAmountVoucher::new),

    PERCENT_DISCOUNT_VOUCHER("percent", PercentDiscountVoucher::new);

    private final String voucherType;
    private final BiFunction<Long, Long, Voucher> voucherGenerator;

    VoucherGeneratorEnum(String voucherType, BiFunction<Long, Long, Voucher> voucherGenerator) {
        this.voucherType = voucherType;
        this.voucherGenerator = voucherGenerator;
    }

    public static Voucher generateVoucher(Long voucherId, Long discountValue, String findVoucherType) {
        VoucherGeneratorEnum findVoucherGenerator = findVoucherGenerator(findVoucherType);
        Voucher generatedVoucher = findVoucherGenerator.voucherGenerator.apply(voucherId, discountValue);
        return generatedVoucher;
    }

    private static VoucherGeneratorEnum findVoucherGenerator(String findVoucherType) {
        return Arrays.stream(VoucherGeneratorEnum.values())
                .filter(voucherGenerator -> voucherGenerator.voucherType.equals(findVoucherType))
                .findFirst()
                .orElseThrow(() -> new NullPointerException());
    }
}
