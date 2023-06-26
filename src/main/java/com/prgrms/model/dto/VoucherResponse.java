package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class VoucherResponse {
    private VoucherPolicy voucherPolicy;
    private Discount discount;

    public static VoucherResponse of(Voucher voucher) {
        return VoucherResponse.builder()
                .voucherPolicy(voucher.getVoucherPolicy())
                .discount(voucher.getVoucherDiscount())
                .build();
    }
}
