package com.programmers.voucher.domain.voucher.dto;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private final UUID voucherId;
    private final VoucherType voucherType;
    private final long amount;
    private final LocalDateTime createdAt;

    VoucherDto(UUID voucherId, VoucherType voucherType, long amount, LocalDateTime createdAt) {
        this.voucherId = voucherId;
        this.voucherType = voucherType;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static VoucherDto from(Voucher voucher) {
        VoucherDtoConverter voucherDtoConverter = new VoucherDtoConverter();
        return voucherDtoConverter.convert(voucher);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
