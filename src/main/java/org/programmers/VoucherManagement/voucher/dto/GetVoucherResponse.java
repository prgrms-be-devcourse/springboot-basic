package org.programmers.VoucherManagement.voucher.dto;

import lombok.EqualsAndHashCode;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

@EqualsAndHashCode
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

}
