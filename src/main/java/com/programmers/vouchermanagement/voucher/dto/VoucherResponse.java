package com.programmers.vouchermanagement.voucher.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;

public record VoucherResponse(
        UUID voucherId,
        LocalDateTime createdAt,
        BigDecimal discountValue,
        VoucherType voucherType,
        UUID customerId
) {
    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(
                voucher.getVoucherId(),
                voucher.getCreatedAt(),
                voucher.getDiscountValue(),
                voucher.getVoucherType(),
                voucher.getCustomerId()
        );
    }
}
