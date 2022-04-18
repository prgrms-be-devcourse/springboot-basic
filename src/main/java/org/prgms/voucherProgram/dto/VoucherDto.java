package org.prgms.voucherProgram.dto;

import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;

public class VoucherDto {
    private final UUID voucherId;
    private int type;
    private long discountValue;

    public VoucherDto(UUID voucherId, int type, long discountValue) {
        this.voucherId = voucherId;
        this.type = type;
        this.discountValue = discountValue;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getType(), voucher.getDiscountValue());
    }

    public Voucher toEntity() {
        return VoucherType.findByNumber(type)
            .constructor(voucherId, null, discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public String toString() {
        return "voucherId=%s, type=%s, discountValue = %d".formatted(voucherId, VoucherType.findByNumber(type),
            discountValue);
    }
}
