package com.programmers.voucher.voucher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public enum VoucherList {
    FixedAmount("F") {
        public Voucher createVoucher(long value) {
            return VoucherFactory.createVoucher(FixedAmount, value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return VoucherFactory.createVoucher(id, FixedAmount, value);
        }
    },

    PercentDiscount("P") {
        public Voucher createVoucher(long value) {
            return VoucherFactory.createVoucher(PercentDiscount, value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return VoucherFactory.createVoucher(id, PercentDiscount, value);
        }
    },
    ;


    private final String type;

    VoucherList(String type) {
        this.type = type;
    }

    public static Optional<VoucherList> findVoucher(String type) {
        return Arrays.stream(VoucherList.values())
                .filter(voucher -> voucher.name().equals(type))
                .findFirst();
    }

    public static VoucherList findVoucherList(String type) {
        return Arrays.stream(VoucherList.values())
                .filter(voucher -> voucher.type.equals(type))
                .findFirst()
                .get();
    }

    public static boolean isValidateVoucherType(String inputType) {
        return Arrays.stream(VoucherList.values())
                .anyMatch(voucher -> voucher.type.equals(inputType));
    }

    public String getType() {
        return type;
    }

    public abstract Voucher createVoucher(long value);

    public abstract Voucher createVoucher(UUID id, long value);
}
