package com.programmers.springbasic.domain.voucher.dto.request;

import lombok.Getter;


@Getter
public class VoucherOptionDTO {
    private static final String VALID_VOUCHER_OPTION_REGEXP = "^(PERCENT|FIXED)$";
    private static final String INVALID_VOUCHER_OPTION_MESSAGE = "지원하지 않는 Voucher Option 입니다.";

    private String voucherOption;

    public VoucherOptionDTO(String voucherOption) {
        if (!voucherOption.matches(VALID_VOUCHER_OPTION_REGEXP)) {
            throw new IllegalArgumentException(INVALID_VOUCHER_OPTION_MESSAGE);
        }
        this.voucherOption = voucherOption;
    }
}
