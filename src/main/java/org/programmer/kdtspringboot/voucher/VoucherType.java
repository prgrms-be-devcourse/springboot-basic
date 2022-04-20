package org.programmer.kdtspringboot.voucher;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum VoucherType {
    FixedAmountVoucher("FIX",((amount) -> new FixedAmountVoucher(UUID.randomUUID(),amount))),
    PercentDiscountVoucher("PERCENT",((percent) -> new PercentDiscountVoucher(UUID.randomUUID(),percent)));

    //추가속성 부여 가능
    private final String type;
    private final Function<Long, Voucher> voucher;

    VoucherType(String type, Function<Long, Voucher> voucher) {
        this.type = type;
        this.voucher = voucher;
    }

    public String getType(){
        return type;
    }

    public static VoucherType getVoucherType(String type){
        return Arrays.stream(VoucherType.values())
                .filter(target -> target.getType().equals(type.toUpperCase()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("voucherType 잘못넣으셨습니다."));
    }

    public Voucher create(Long value){
        return voucher.apply(value);
    }


}
