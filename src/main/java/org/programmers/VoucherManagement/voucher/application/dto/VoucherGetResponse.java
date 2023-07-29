package org.programmers.VoucherManagement.voucher.application.dto;

import lombok.Builder;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

@Builder
public record VoucherGetResponse(
        String voucherId,
        DiscountType discountType,
        int discountValue
) {
    public static VoucherGetResponse toDto(Voucher voucher) {

        return VoucherGetResponse.builder()
                .voucherId(voucher.getVoucherId())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue().getValue())
                .build();
    }
}
