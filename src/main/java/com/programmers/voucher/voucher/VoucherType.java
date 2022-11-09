package com.programmers.voucher.voucher;

import java.util.List;
import java.util.UUID;


public enum VoucherType {
    FixedAmount(List.of("F", "FixedAmount")) {
        public Voucher createVoucher(long value) {
            return VoucherFactory.createVoucher(this, value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return VoucherFactory.createVoucher(id, this, value);
        }
    },

    PercentDiscount(List.of("P", "PercentDiscount")) {
        public Voucher createVoucher(long value) {
            return VoucherFactory.createVoucher(this, value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return VoucherFactory.createVoucher(id, this, value);
        }
    },
    ;

    private final List<String> type;

    VoucherType(List<String> type) {
        this.type = type;
    }

    public List<String> getType() {
        return type;
    }

    public abstract Voucher createVoucher(long value);

    public abstract Voucher createVoucher(UUID id, long value);
}
