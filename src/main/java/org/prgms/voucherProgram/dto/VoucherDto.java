package org.prgms.voucherProgram.dto;

import java.util.UUID;

import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.domain.voucher.VoucherType;

public class VoucherDto {
    private final UUID voucherId;
    private final String type;
    private final long discountValue;

    public VoucherDto(UUID voucherId, int type, long discountValue) {
        this.voucherId = voucherId;
        this.type = VoucherType.findByNumber(type).toString();
        this.discountValue = discountValue;
    }

    public static VoucherDto from(Voucher voucher) {
        return new VoucherDto(voucher.getVoucherId(), voucher.getType(), voucher.getDiscountValue());
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
}
