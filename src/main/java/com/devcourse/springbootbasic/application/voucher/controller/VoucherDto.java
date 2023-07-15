package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.model.DiscountValue;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.voucher.model.VoucherType;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record VoucherDto(
        UUID voucherId, VoucherType voucherType, DiscountValue discountValue, LocalDateTime createdAt, Optional<UUID> customerId
) {

    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getVoucherType(),
                voucher.getDiscountValue(),
                voucher.getCreatedAt(),
                voucher.getCustomerId()
        );
    }

    public Voucher to() {
        return new Voucher(voucherId(), voucherType(), discountValue(), createdAt(), customerId());
    }

    @Override
    public String toString() {
        if (customerId().isEmpty()) {
            return MessageFormat.format(
                    "Voucher(id: {0}, voucherType: {1}, discountValue: {2}, customerId: {3})",
                    voucherId, voucherType, discountValue.getValue(), customerId
            );
        }
        return MessageFormat.format(
                "Voucher(id: {0}, voucherType: {1}, discountValue: {2}, createdAt: {3}, customerId: {4})",
                voucherId, voucherType, discountValue.getValue(), createdAt, customerId
        );
    }
}