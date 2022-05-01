package org.prgrms.voucher.models;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum VoucherType {

    FIXED_AMOUNT("1") {
        @Override
        public Voucher createVoucher(long discountValue, VoucherType voucherType) {

            return new FixedAmountVoucher(discountValue, voucherType);
        }

        @Override
        public Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType,
                                     LocalDateTime createdAt, LocalDateTime updatedAt)
        {

            return new FixedAmountVoucher(voucherId, discountValue, voucherType, createdAt, updatedAt);
        }
    },
    PERCENT_DISCOUNT("2") {
        @Override
        public Voucher createVoucher(long discountValue, VoucherType voucherType) {

            return new PercentDiscountVoucher(discountValue, voucherType);
        }

        @Override
        public Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType,
                                     LocalDateTime createdAt, LocalDateTime updatedAt) {

            return new PercentDiscountVoucher(voucherId, discountValue, voucherType, createdAt, updatedAt);
        }
    };

    private final String typeNumber;

    VoucherType(String typeNumber) {

        this.typeNumber = typeNumber;
    }

    public static VoucherType findByUserInput(String userInput) {

        return Arrays.stream(VoucherType.values())
                .filter(v -> v.typeNumber.equals(userInput))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public abstract Voucher createVoucher(long discountValue, VoucherType voucherType);

    public abstract Voucher createVoucher(Long voucherId, long discountValue, VoucherType voucherType,
                                          LocalDateTime createdAt, LocalDateTime updatedAt);
}
