package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.VoucherType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class VoucherRequest {
    private final String exceptionMessage = "기본생성자는 만들 수 없습니다.";

    private VoucherType voucherType;
    private Discount discount;

    public VoucherRequest() {
        throw new IllegalArgumentException(exceptionMessage);
    }

    public static VoucherRequest of(VoucherType voucherType, Discount discount) {
        return VoucherRequest.builder()
                .voucherType(voucherType)
                .discount(discount)
                .build();
    }

}
