package org.programmers.VoucherManagement.voucher.dto;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.Objects;
import java.util.UUID;

public class GetVoucherResponse {
    private final UUID voucherId;
    private final DiscountType discountType;
    private final int discountValue;

    public GetVoucherResponse(UUID voucherId, DiscountType discountType, int discountValue) {
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public UUID getVoucherId() {
        return voucherId;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public static GetVoucherResponse toDto(Voucher voucher) {
        return new GetVoucherResponse(voucher.getVoucherId()
                , voucher.getDiscountType()
                , voucher.getDiscountValue().getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetVoucherResponse that = (GetVoucherResponse) o;
        return discountValue == that.discountValue && voucherId.equals(that.voucherId) && discountType == that.discountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountType, discountValue);
    }

}
