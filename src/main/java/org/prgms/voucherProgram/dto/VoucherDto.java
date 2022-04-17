package org.prgms.voucherProgram.dto;

import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;

public class VoucherDto {
    private final UUID voucherId;
    private final UUID customerId;
    private final int type;
    private long discountValue;

    public VoucherDto(UUID voucherId, UUID customerId, int type, long discountValue) {
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.type = type;
        this.discountValue = discountValue;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getCustomerId(), voucher.getType(),
            voucher.getDiscountValue());
    }

    public Voucher toEntity() {
        return VoucherType.findByNumber(type)
            .createVoucher(voucherId, customerId, discountValue);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public int getType() {
        return type;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public String toString() {
        return "voucherId=%s, customerId=%s, type=%s, discountValue = %d".formatted(voucherId, customerId,
            VoucherType.findByNumber(type), discountValue);
    }
}
