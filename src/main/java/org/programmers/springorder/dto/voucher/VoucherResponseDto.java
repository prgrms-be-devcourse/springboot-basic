package org.programmers.springorder.dto.voucher;

import org.programmers.springorder.model.voucher.Voucher;
import org.programmers.springorder.model.voucher.VoucherType;

import java.util.UUID;


public class VoucherResponseDto {
    private final UUID voucherId;
    private final long discountValue;
    private final VoucherType voucherType;

    private VoucherResponseDto(Voucher voucher) {
        this.voucherId = voucher.getVoucherId();
        this.discountValue = voucher.getDiscountValue();
        this.voucherType = voucher.getVoucherType();
    }

    public static VoucherResponseDto of(Voucher voucher) {
        return new VoucherResponseDto(voucher);
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public VoucherType getVoucherType() {
        return voucherType;
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
