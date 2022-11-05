package com.example.springbootbasic.domain.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

public enum VoucherEnum {
    FIXED_AMOUNT_VOUCHER("fixed", FixedAmountVoucher::new),
    PERCENT_DISCOUNT_VOUCHER("percent", PercentDiscountVoucher::new);

    private final String voucherType;
    private final BiFunction<Long, Long, Voucher> voucherGenerator;

    VoucherEnum(String voucherType, BiFunction<Long, Long, Voucher> voucherGenerator) {
        this.voucherType = voucherType;
        this.voucherGenerator = voucherGenerator;
    }

    public static Voucher generateVoucher(Long voucherId, Long discountValue, VoucherEnum findVoucherGenerator) {
        return findVoucherGenerator.voucherGenerator.apply(voucherId, discountValue);
    }

    public static Optional<VoucherEnum> findVoucherGenerator(String findVoucherType) {
        return Arrays.stream(VoucherEnum.values())
                .filter(voucherGenerator -> voucherGenerator.voucherType.equals(findVoucherType))
                .findFirst();
    }

    public String getVoucherType() {
        return voucherType;
    }
}
