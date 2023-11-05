package org.programmers.springorder.voucher.dto;

import org.programmers.springorder.voucher.model.Voucher;

import java.util.Objects;
import java.util.UUID;


public final class VoucherResponseDto {
    private final UUID voucherId;
    private final long discountValue;
    private final String voucherType;
    private final String customerId;

    public VoucherResponseDto(UUID voucherId, long discountValue, String voucherType, UUID customerId) {
        this.voucherId = voucherId;
        this.discountValue = discountValue;
        this.voucherType = voucherType;
        if (customerId != null) {
            this.customerId = customerId.toString();
        } else {
            this.customerId = "";
        }
    }


    public static VoucherResponseDto of(Voucher voucher) {
        return new VoucherResponseDto(
                voucher.getVoucherId(),
                voucher.getDiscountValue(),
                voucher.getVoucherType().name(),
                voucher.getCustomerId());
    }

    @Override
    public String toString() {
        return "ID : " + voucherId + '\n' +
                "Type : " + voucherType + '\n' +
                "Value : " + discountValue + '\n' +
                "==============================";
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (VoucherResponseDto) obj;
        return Objects.equals(this.voucherId, that.voucherId) &&
                this.discountValue == that.discountValue &&
                Objects.equals(this.voucherType, that.voucherType) &&
                Objects.equals(this.customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountValue, voucherType, customerId);
    }

}
