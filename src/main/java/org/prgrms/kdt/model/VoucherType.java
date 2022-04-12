package org.prgrms.kdt.model;

import org.prgrms.kdt.util.Utility;

import java.util.Arrays;
import java.util.UUID;

public enum VoucherType {
    FIXED_AMOUNT(1) {
        @Override
        public Voucher createVoucherByType(UUID voucherId, int discountAmount) {
            return new FixedAmountVoucher(voucherId, discountAmount);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher createVoucherByType(UUID voucherId, int discountAmount) {
            return new PercentDiscountVoucher(voucherId, discountAmount);
        }
    };

    private final Integer typeNumber;

    VoucherType(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    public static boolean hasVoucherType(String inputString) {
        if (!Utility.isNumber(inputString)) return false;
        return Arrays.stream(VoucherType.values())
                .anyMatch(v -> v.typeNumber == Utility.toInt(inputString));
    }

    public Integer getTypeNumber() {
        return typeNumber;
    }

    public abstract Voucher createVoucherByType(UUID voucherId, int discountAmount);
}
