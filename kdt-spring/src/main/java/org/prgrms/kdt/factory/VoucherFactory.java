package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherFactory {
    public static Voucher createVoucher(UUID id, VoucherType type, long value, LocalDateTime createdAt) {
        return switch (type) {
            case FIX -> new FixedAmountVoucher(id, value, createdAt);
            case PERCENT -> new PercentAmountVoucher(id, value, createdAt);
            default -> throw new IllegalArgumentException("Wrong Voucher Type");
        };

    }
}