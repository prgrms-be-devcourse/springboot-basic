package com.programmers.springbasic.domain.voucher.validator;

import lombok.Getter;

@Getter
public class VoucherCodeValidator {
    private static final String VALID_VOUCHER_CODE_REGEXP = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    private static final String INVALID_VOUCHER_CODE_MESSAGE = "uuid 형식이 아닙니다.";

    private String inputVoucherCode;

    public VoucherCodeValidator(String inputVoucherCode) {
        if (!inputVoucherCode.matches(VALID_VOUCHER_CODE_REGEXP)) {
            throw new IllegalArgumentException(INVALID_VOUCHER_CODE_MESSAGE);
        }

        this.inputVoucherCode = inputVoucherCode;
    }
}
