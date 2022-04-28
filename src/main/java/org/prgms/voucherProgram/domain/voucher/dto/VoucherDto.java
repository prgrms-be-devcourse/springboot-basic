package org.prgms.voucherProgram.domain.voucher.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.prgms.voucherProgram.domain.voucher.domain.VoucherType;

public class VoucherDto {
    private final UUID voucherId;
    private final String type;
    private final long discountValue;
    private final LocalDateTime createdTime;

    public VoucherDto(UUID voucherId, String type, long discountValue, LocalDateTime createdTime) {
        this.voucherId = voucherId;
        this.type = type;
        this.discountValue = discountValue;
        this.createdTime = createdTime;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), VoucherType.findByNumber(voucher.getType()).toString(),
            voucher.getDiscountValue(),
            voucher.getCreatedTime());
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public String getType() {
        return type;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
}
