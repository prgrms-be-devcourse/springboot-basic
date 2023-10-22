package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class VoucherResponse {

    private final UUID id;
    private final String voucherType;
    private final Integer benefitValue;

    public VoucherResponse(UUID id, String voucherType, Integer benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    @Override
    public String toString() {
        return """
            Voucher id = %s
            Type = %s
            benefitValue = %s
            ===============================
            """.formatted(id, voucherType, benefitValue);
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getVoucherType().getVoucherTypeName(),
            voucher.getBenefitValue());
    }

}
