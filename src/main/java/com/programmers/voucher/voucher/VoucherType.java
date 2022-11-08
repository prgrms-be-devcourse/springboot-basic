package com.programmers.voucher.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherType {
    FixedAmount("F") {
        public Voucher createVoucher(long value) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new FixedAmountVoucher(id, value);
        }
    },

    PercentDiscount("P") {
        public Voucher createVoucher(long value) {
            return new PercentDiscountVoucher(UUID.randomUUID(), value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new PercentDiscountVoucher(id, value);
        }
    },
    ;


    private final String type;

    VoucherType(String type) {
        this.type = type;
    }

    public static Optional<VoucherType> findVoucher(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.name().equals(type))
                .findFirst();
    }

    public static VoucherType findVoucherType(String type) {
        return Arrays.stream(VoucherType.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst()
                .get();
    }

    public static boolean isValidateVoucherType(String inputType) {
        return Arrays.stream(VoucherType.values())
                .anyMatch(voucher -> voucher.type.equals(inputType));
    }

    public String getType() {
        return type;
    }

    public abstract Voucher createVoucher(long value);

    public abstract Voucher createVoucher(UUID id, long value);
}
