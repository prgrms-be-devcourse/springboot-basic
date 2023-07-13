package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.entity.Voucher;
import com.programmers.voucher.domain.voucher.entity.VoucherType;

import java.util.UUID;

public record VoucherResponse(
        UUID voucherId,
        VoucherType voucherType,
        int amount
) {
    public static VoucherResponse from(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getType(), voucher.getAmount());
    }

    @Override
    public String toString() {
        return switch (voucherType) {
            case FIXED -> String.format("[%s] %d discount voucher", voucherId, amount);
            case PERCENT -> String.format("[%s] %d%% discount voucher", voucherId, amount);
        };
    }
}
