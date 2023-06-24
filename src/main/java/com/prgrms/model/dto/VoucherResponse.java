package com.prgrms.model.dto;

import com.prgrms.model.voucher.Voucher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class VoucherResponse {
    private String voucherType;
    private long discount;

    public static VoucherResponse of(Voucher voucher){
        return VoucherResponse.builder()
                .voucherType(voucher.getVoucherType())
                .discount(voucher.getVoucherDiscount())
                .build();
    }
}
