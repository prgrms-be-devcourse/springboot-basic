package org.prgms.voucherProgram.voucher.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgms.voucherProgram.voucher.domain.Voucher;
import org.prgms.voucherProgram.voucher.domain.VoucherType;

public class VoucherDto {
    private final UUID voucherId;
    private final String type;
    private final long discountValue;
    private final LocalDateTime createdDateTime;

    public VoucherDto(UUID voucherId, String type, long discountValue, LocalDateTime createdDateTime) {
        this.voucherId = voucherId;
        this.type = type;
        this.discountValue = discountValue;
        this.createdDateTime = createdDateTime;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), VoucherType.findByNumber(voucher.getType()).toString(),
            voucher.getDiscountValue(),
            voucher.getCreatedDateTime());
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }
}
