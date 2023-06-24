package com.programmers.springbasic.domain.voucher.entity;

import lombok.Getter;

@Getter
public class PercentDiscountVoucher extends Voucher {
    private static final String PERCENT_VOUCHER_INFO_PREFIX = "== PercentDiscount Voucher Info ==";
    private static final String PERCENT_DISCOUNT_VOUCHER_ID_INFO = "voucherID: ";
    private static final String PERCENT_DISCOUNT_VOUCHER_VALUE_INFO = "value: ";
    private static final String PERCENT_DISCOUNT_VOUCHER_EXPIRE_INFO = "expireDate: ";
    private static final String PERCENT_DISCOUNT_VOUCHER_VALID_INFO = "valid: ";

    public PercentDiscountVoucher(double percent) {
        super();
        this.value = percent;
    }

    @Override
    public String getInfo() {
        StringBuilder voucherInfo = new StringBuilder();

        voucherInfo.append(PERCENT_VOUCHER_INFO_PREFIX + "\n")
                .append(PERCENT_DISCOUNT_VOUCHER_ID_INFO + code + "\n")
                .append(PERCENT_DISCOUNT_VOUCHER_VALUE_INFO + String.format("%.0f%%", value) + "\n")
                .append(PERCENT_DISCOUNT_VOUCHER_EXPIRE_INFO + expirationDate + "\n")
                .append(PERCENT_DISCOUNT_VOUCHER_VALID_INFO + isActive + "\n");

        return voucherInfo.toString();
    }
}
