package org.prgrms.kdtbespring.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT_VOUCHER("1"){
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long amount) {
            return new FixedAmountVoucher(voucherId, amount);
        }
    },
    PERCENT_DISCOUNT_VOUCHER("2"){
        @Override
        Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long percent) {
            return new PercentDiscountVoucher(voucherId, percent);
        }
    };

    abstract Voucher voucherCreate(VoucherRepository voucherRepository, UUID voucherId, long value);

    String value;

    VoucherType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<VoucherType> of(String value) {
        return Arrays.stream(VoucherType.values())
                .filter(voucherType -> voucherType.getValue().equals(value))
                .findFirst();
    }

}
