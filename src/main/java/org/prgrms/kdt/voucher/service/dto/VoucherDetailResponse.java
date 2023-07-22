package org.prgrms.kdt.voucher.service.dto;

import org.prgrms.kdt.voucher.domain.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDetailResponse(UUID voucherId, String voucherType, double amount, LocalDateTime createdAt) {
    public VoucherDetailResponse(Voucher voucher) {
        this(voucher.getVoucherId(), voucher.getVoucherType().getDescripton(), voucher.getDiscountPolicy().getAmount(), voucher.getCreatedAt());
    }
}
