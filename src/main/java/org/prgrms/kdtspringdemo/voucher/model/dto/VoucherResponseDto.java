package org.prgrms.kdtspringdemo.voucher.model.dto;

import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;

import java.util.UUID;

public class VoucherResponseDto {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;

    public VoucherResponseDto(UUID voucherId, VoucherType voucherType, long amount) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public long getAmount() {
        return amount;
    }

    public static VoucherResponseDto toDto(UUID voucherId, VoucherType voucher, long amount) {
        return new VoucherResponseDto(
                voucherId,
                voucher,
                amount
        );
    }
}
