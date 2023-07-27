package org.programmers.VoucherManagement.voucher.application.dto;

import lombok.Builder;
import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;

import java.util.UUID;

@Builder
public record VoucherGetResponse(
        UUID voucherId,
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
