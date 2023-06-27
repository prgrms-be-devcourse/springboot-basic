package org.programmers.VoucherManagement.voucher.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.VoucherManagement.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

@Getter
public class GetVoucherResponse {
    UUID voucherId;
    DiscountType discountType;
    int discountValue;

    public GetVoucherResponse(UUID voucherId, DiscountType discountType, int discountValue){
        this.voucherId = voucherId;
        this.discountType = discountType;
        this.discountValue = discountValue;
    }

    public static GetVoucherResponse toDto(Voucher voucher){
        return new GetVoucherResponse(voucher.getVoucherId(), voucher.getDiscountType(), voucher.getDiscountValue());
    }
}
