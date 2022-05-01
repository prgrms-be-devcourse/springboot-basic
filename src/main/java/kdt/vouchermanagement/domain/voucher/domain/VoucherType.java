package kdt.vouchermanagement.domain.voucher.domain;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum VoucherType {
    NONE(0) {
        @Override
        public Voucher create(int discountValue) {
            return null;
        }

        @Override
        public Voucher createEntity(Long voucherId, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
            return null;
        }


    },
    FIXED_AMOUNT(1) {
        @Override
        public Voucher create(int discountValue) {
            return new FixedAmountVoucher(VoucherType.FIXED_AMOUNT, discountValue);
        }

        @Override
        public Voucher createEntity(Long voucherId, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
            return new FixedAmountVoucher(voucherId, VoucherType.FIXED_AMOUNT, discountValue, createdAt, updatedAt);
        }
    },
    PERCENT_DISCOUNT(2) {
        @Override
        public Voucher create(int discountValue) {
            return new PercentDiscountVoucher(VoucherType.PERCENT_DISCOUNT, discountValue);
        }

        @Override
        public Voucher createEntity(Long voucherId, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
            return new PercentDiscountVoucher(voucherId, VoucherType.PERCENT_DISCOUNT, discountValue, createdAt, updatedAt);
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

    public abstract Voucher createEntity(Long voucherId, int discountValue, LocalDateTime createdAt, LocalDateTime updatedAt);
}
