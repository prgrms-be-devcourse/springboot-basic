package org.prgrms.dev.voucher.domain;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FIXED("f", v-> new FixedAmountVoucher(UUID.randomUUID(), v)),
    PERCENT("p", v-> new PercentDiscountVoucher(UUID.randomUUID(), v));

    private String type;

    private Function<Long, Voucher> voucherMaker;

    VoucherType(String type, Function<Long, Voucher> voucherMaker)
    {
        this.type = type;
        this.voucherMaker = voucherMaker;
    }

    public Voucher create(Long value){
        return this.voucherMaker.apply(value);
    }

    public static Voucher getVoucherType(String inputType, long value){
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(inputType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        return voucherType.create(value);
    }
}
