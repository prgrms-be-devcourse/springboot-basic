package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;

import lombok.*;


@Getter
@Builder
@AllArgsConstructor
public class VoucherResponse {
    private final String exceptionMessage = "기본생성자는 만들 수 없습니다.";
    private VoucherPolicy voucherPolicy;
    private Discount discount;

    public VoucherResponse() {
        throw new IllegalArgumentException(exceptionMessage);
    }

    public long getDiscountedValue() {
        return discount.getDiscount();
    }

    public static VoucherResponse of(Voucher voucher) {
        return VoucherResponse.builder()
                .voucherPolicy(voucher.getVoucherPolicy())
                .discount(voucher.getVoucherDiscount())
                .build();
    }
}
