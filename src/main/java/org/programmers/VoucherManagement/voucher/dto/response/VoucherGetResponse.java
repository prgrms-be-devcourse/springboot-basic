package org.programmers.VoucherManagement.voucher.dto.response;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.Objects;
import java.util.UUID;

public class VoucherGetResponse {
    private final UUID voucherId;
    private final DiscountType discountType;
    private final int discountValue;

    public VoucherGetResponse(UUID voucherId, DiscountType discountType, int discountValue) {
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

    public static VoucherGetResponse toDto(Voucher voucher) {
        return new VoucherGetResponse(voucher.getVoucherId()
                , voucher.getDiscountType()
                , voucher.getDiscountValue().getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherGetResponse that = (VoucherGetResponse) o;
        return discountValue == that.discountValue && voucherId.equals(that.voucherId) && discountType == that.discountType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(voucherId, discountType, discountValue);
    }

}
