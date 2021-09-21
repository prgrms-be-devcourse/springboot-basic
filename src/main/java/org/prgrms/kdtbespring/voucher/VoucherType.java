package org.prgrms.kdtbespring.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1","FixedAmountVoucher"){
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount);
        }
    },
    PERCENT_DISCOUNT_VOUCHER("2","PercentDiscountVoucher"){
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long percent) {
            return new PercentDiscountVoucher(voucherId, percent);
        }
    };

    abstract Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long value);

    String value;
    String voucherName;

    VoucherType(String value, String voucherName) {
        this.value = value;
        this.voucherName = voucherName;
    }

    public String getValue() {
        return value;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public static Optional<VoucherType> of(String value) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getValue().equals(value))
                .findFirst();
    }

    public static Optional<VoucherType> findByName(String voucherName){
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getVoucherName().equals(voucherName))
                .findFirst();
    }

}
