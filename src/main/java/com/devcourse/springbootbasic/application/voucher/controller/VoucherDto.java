package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;

import java.text.MessageFormat;
import java.util.UUID;

public record VoucherDto(
        UUID voucherId, VoucherType voucherType, DiscountValue discountValue
) {
    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getVoucherType(),
                voucher.getDiscountValue()
        );
    }

    public static Voucher to(VoucherDto voucherDto) {
        return new Voucher(
                voucherDto.voucherId(),
                voucherDto.voucherType(),
                voucherDto.discountValue()
        );
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "Voucher(id: {0}, voucherType: {1}, discountValue: {2})",
                voucherId, voucherType, discountValue.value()
        );
    }
}