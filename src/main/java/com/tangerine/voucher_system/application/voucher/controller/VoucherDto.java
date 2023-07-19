package com.tangerine.voucher_system.application.voucher.controller;

import com.tangerine.voucher_system.application.voucher.model.DiscountValue;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.model.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;

public record VoucherDto(
        UUID voucherId,
        VoucherType voucherType,
        DiscountValue discountValue,
        LocalDate createdAt
) {

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.voucherId(),
                voucher.voucherType(),
                voucher.discountValue(),
                voucher.createdAt()
        );
    }

    public Voucher to() {
        return new Voucher(voucherId(), voucherType(), discountValue(), createdAt());
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Voucher(id: {0}, voucherType: {1}, discountValue: {2}, createdAt:{3})",
                voucherId, voucherType, discountValue.getValue(), createdAt
        );
    }
}