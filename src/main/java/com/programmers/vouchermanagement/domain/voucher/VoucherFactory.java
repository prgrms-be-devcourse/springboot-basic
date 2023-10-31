package com.programmers.vouchermanagement.domain.voucher;

import com.programmers.vouchermanagement.dto.VoucherDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {

    private VoucherFactory() {
    }

    public static Voucher createVoucher(String[] voucherInfo) {
        final UUID voucherId = UUID.fromString(voucherInfo[0]);
        final String voucherName = voucherInfo[1];
        final float discountAmount = Float.parseFloat(voucherInfo[2]);
        final LocalDateTime createdAt = LocalDateTime.parse(voucherInfo[3]);
        final VoucherType voucherType = VoucherType.valueOf(voucherInfo[4]);

        return switch (voucherType) {
            case FIXED -> new FixedAmountVoucher(voucherId, voucherName, discountAmount, createdAt);
            case PERCENTAGE -> new PercentDiscountVoucher(voucherId, voucherName, discountAmount, createdAt);
        };
    }

    public static Voucher createVoucher(VoucherDto.CreateRequest createRequestDto) {
        if (createRequestDto.voucherId == null) createRequestDto.voucherId = UUID.randomUUID();
        if (createRequestDto.createdAt == null) createRequestDto.createdAt = LocalDateTime.now();
        return switch (createRequestDto.voucherType) {
            case FIXED ->
                    new FixedAmountVoucher(createRequestDto.voucherId, createRequestDto.voucherName, createRequestDto.discountAmount, createRequestDto.createdAt);
            case PERCENTAGE ->
                    new PercentDiscountVoucher(createRequestDto.voucherId, createRequestDto.voucherName, createRequestDto.discountAmount, createRequestDto.createdAt);
        };
    }
}
