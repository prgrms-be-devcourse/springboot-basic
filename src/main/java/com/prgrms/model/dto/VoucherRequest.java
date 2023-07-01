package com.prgrms.model.dto;

import com.prgrms.model.voucher.Discount;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class VoucherRequest {
    private final String exceptionMessage = "기본생성자는 만들 수 없습니다.";

    private VoucherPolicy voucherPolicy;
    private Discount discount;

    public VoucherRequest() {
        throw new IllegalArgumentException(exceptionMessage);
    }

    public static VoucherRequest of(VoucherPolicy voucherPolicy, Discount discount) {
        return VoucherRequest.builder()
                .voucherPolicy(voucherPolicy)
                .discount(discount)
                .build();
    }

}
