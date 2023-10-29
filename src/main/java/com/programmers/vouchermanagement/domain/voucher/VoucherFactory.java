package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.dto.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher createVoucher(VoucherDto.Create createDto) {
        if (createDto.voucherId == null) createDto.voucherId = UUID.randomUUID();
        if (createDto.createdAt == null) createDto.createdAt = LocalDateTime.now();
        return switch (createDto.voucherType) {
            case FIXED ->
                    new FixedAmountVoucher(createDto.voucherId, createDto.voucherName, createDto.discountAmount, createDto.createdAt);
            case PERCENTAGE ->
                    new PercentDiscountVoucher(createDto.voucherId, createDto.voucherName, createDto.discountAmount, createDto.createdAt);
        };
    }
}
