package com.programmers.voucher.voucher;

import com.programmers.voucher.menu.Message;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.voucher.menu.Message.INPUT_ERROR_MESSAGE;

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
        return Arrays.stream(values())
                .filter(voucher -> voucher.name().equals(type))
                .findFirst();
    }

    public static VoucherType getValidateVoucherType(String inputType) {
        return  Arrays.stream(values())
                .filter(voucherType -> voucherType.getType().equals(inputType))
                .findFirst().orElseThrow(() -> new RuntimeException(INPUT_ERROR_MESSAGE.getMessage()));
    }

    public String getType() {
        return type;
    }

    public abstract Voucher createVoucher(long value);

    public abstract Voucher createVoucher(UUID id, long value);
}
