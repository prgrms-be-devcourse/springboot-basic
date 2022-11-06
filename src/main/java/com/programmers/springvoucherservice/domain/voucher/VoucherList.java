package com.programmers.springvoucherservice.domain.voucher;

import com.programmers.springvoucherservice.VoucherFactory;

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

    PercentDiscount("P"){
        public Voucher createVoucher(long value) {
            return VoucherFactory.createVoucher(PercentDiscount, value);
        }

        @Override
        public Voucher createVoucher(UUID id, long value) {
            return VoucherFactory.createVoucher(id, PercentDiscount, value);
        }
    };


    private String type;

    VoucherList(String type) {
        this.type = type;
    }

    public static Optional<VoucherList> findVoucher(String type) {
        return Arrays.stream(VoucherList.values())
                .filter(voucher -> voucher.name().equals(type))
                .findFirst();
    }

    public abstract Voucher createVoucher(long value);
    public abstract Voucher createVoucher(UUID id, long value);
}
