package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import java.util.UUID;

public class VoucherResponse {
    private UUID id;
    private String voucherType;
    private Integer benefitValue;

    public VoucherResponse(UUID id, String voucherType, Integer benefitValue) {
        this.id = id;
        this.voucherType = voucherType;
        this.benefitValue = benefitValue;
    }

    @Override
    public String toString() {
        return
            "Voucher id =" + id + System.lineSeparator() +
                "Type =" + voucherType + System.lineSeparator() +
                "benefitValue =" + benefitValue + System.lineSeparator() +
                "===============================" + System.lineSeparator();
    }

    public static VoucherResponse of(Voucher voucher) {
        return new VoucherResponse(voucher.getId(), voucher.getVoucherType().getVoucherTypeName(), voucher.getBenefitValue());
    }

}
