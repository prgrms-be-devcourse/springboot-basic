package com.programmers.voucher.voucher;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.menu.Message.VOUCHER_INPUT_ERROR_MESSAGE;

public enum VoucherType {
    FixedAmount(List.of("F", "FixedAmount")) {
        public Voucher createVoucher(long value) {
            return new FixedAmountVoucher(UUID.randomUUID(), value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new FixedAmountVoucher(id, value);
        }
    },

    PercentDiscount(List.of("P", "PercentDiscount")) {
        public Voucher createVoucher(long value) {
            return new PercentDiscountVoucher(UUID.randomUUID(), value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return new PercentDiscountVoucher(id, value);
        }
    },
    ;

    private final List<String> type;

    VoucherType(List<String> type) {
        this.type = type;
    }

    public static VoucherType getValidateVoucherType(String inputType) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getType().contains(inputType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(VOUCHER_INPUT_ERROR_MESSAGE.getMessage()));
    }

    public List<String> getType() {
        return type;
    }

    public abstract Voucher createVoucher(long value);

    public abstract Voucher createVoucher(UUID id, long value);
}
