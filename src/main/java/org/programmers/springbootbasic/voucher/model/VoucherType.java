package org.programmers.springbootbasic.voucher.model;

import java.util.Arrays;
import java.util.function.Function;

public enum VoucherType {
    FIXED(1, "FixedAmountVoucher", FixedAmountVoucher::createVoucher),
    PERCENT(2, "PercentDiscountVoucher", PercentDiscountVoucher::createVoucher);

    private final int number;
    private final String type;
    private final Function<VoucherDTO, Voucher> voucherFunction;

    VoucherType(int number, String type, Function<VoucherDTO, Voucher> voucherFunction) {
        this.number = number;
        this.type = type;
        this.voucherFunction = voucherFunction;
    }

    public static VoucherType findByNumber(int number) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.equalsNumber(number))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 넘버입니다."));
    }

    public static VoucherType findByType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 바우처 타입입니다."));
    }

    public Voucher create(VoucherDTO voucherDTO) {
        return voucherFunction.apply(voucherDTO);
    }

    public boolean equalsNumber(int findNumber) {
        return (this.number == findNumber);
    }
}
