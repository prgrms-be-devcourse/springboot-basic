package org.prgrms.voucherapp.engine.voucher.dto;

import org.prgrms.voucherapp.engine.voucher.entity.Voucher;

import java.time.LocalDateTime;
import java.util.UUID;

public record VoucherDto(
        UUID voucherId,
        String type,
        long amount,
        LocalDateTime createdAt
) {
    public static VoucherDto of(Voucher voucher) {
        return new VoucherDto(
                voucher.getVoucherId(),
                voucher.getType(),
                voucher.getAmount(),
                voucher.getCreatedAt()
        );
    }
}
