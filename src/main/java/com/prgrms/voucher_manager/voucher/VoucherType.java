package com.prgrms.voucher_manager.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FixedAmountVoucher("FIX",((value) -> new FixedAmountVoucher(UUID.randomUUID(), value))),
    PercentDiscountVoucher("PERCENT", ((value) -> new PercentDiscountVoucher(UUID.randomUUID(), value)));

    private String type;
    private Function<Long, Voucher> voucher;


    VoucherType(String type, Function<Long, Voucher> voucher) {
        this.type = type;
        this.voucher = voucher;
    }

    public static VoucherType getVoucherType(String inputType){
        return Arrays.stream(VoucherType.values())
                .filter(v -> v.getType().equals(inputType.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 voucher type 입니다."));
    }

    public Voucher create(long value){
        return voucher.apply(value);
    }
    private String getType(){
        return type;
    }
}