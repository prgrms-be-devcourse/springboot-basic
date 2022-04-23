package kdt.vouchermanagement.domain.voucher.domain;

import java.util.Arrays;

public enum VoucherType {
    NONE(0) {
        @Override
        public Voucher create(int discountValue) {
            return null;
        }
    },
    FIXED_AMOUNT(1) {
        @Override
        public Voucher create(int discountValue) {
            return new FixedAmountVoucher(VoucherType.FIXED_AMOUNT, discountValue);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher create(int discountValue) {
            return new PercentDiscountVoucher(VoucherType.PERCENT_DISCOUNT, discountValue);
        }
    };

    private int voucherTypeNum;

    VoucherType(int voucherTypeNum) {
        this.voucherTypeNum = voucherTypeNum;
    }

    public static VoucherType from(int voucherTypeNum) {
        VoucherType type = Arrays.stream(values())
                .filter(o -> o.voucherTypeNum == voucherTypeNum)
                .findFirst()
                .orElse(NONE);
        return type;
    }

    public abstract Voucher create(int discountValue);
}
