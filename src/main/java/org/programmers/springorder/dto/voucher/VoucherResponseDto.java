package org.programmers.springorder.dto.voucher;

import org.programmers.springorder.model.voucher.Voucher;

import java.util.UUID;


public class VoucherResponseDto {
    private final UUID voucherId;
    private final long discountValue;
    private final String voucherType;

    private VoucherResponseDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountValue = voucher.getDiscountValue();
        this.voucherType = voucher.getVoucherType().name();
    }

    public static VoucherResponseDto of(Voucher voucher) {
        return new VoucherResponseDto(voucher);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public String toString() {
        return "[" +
                "ID: " + voucherId +
                ", Type: " + voucherType +
                ", Value: " + discountValue +
                "]";
    }
}
