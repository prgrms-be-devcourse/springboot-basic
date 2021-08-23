package org.prgrms.dev.voucher.domain;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED("f"){
        public Voucher create(long value){
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }
    },
    PERCENT("p"){
        public Voucher create(long value){
            return new PercentDiscountVoucher(UUID.randomUUID(), value);
        }
    };

    private String type;

    VoucherType(String type) {
        this.type = type;
    }

    abstract Voucher create(long value);

    public static Voucher getVoucherType(String type, long value){
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        return voucherType.create(value);
    }
}
