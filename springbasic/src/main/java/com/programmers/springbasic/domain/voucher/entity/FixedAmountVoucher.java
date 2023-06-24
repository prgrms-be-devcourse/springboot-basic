package com.programmers.springbasic.domain.voucher.entity;

public class FixedAmountVoucher extends Voucher {
    private static final String FIXED_AMOUNT_VOUCHER_INFO_PREFIX = "== Fixed Amount Voucher Info ==";
    private static final String FIXED_AMOUNT_VOUCHER_ID_INFO = "voucherID: ";
    private static final String FIXED_AMOUNT_VOUCHER_VALUE_INFO = "value: ";
    private static final String FIXED_AMOUNT_VOUCHER_EXPIRE_INFO = "expireDate: ";
    private static final String FIXED_AMOUNT_VOUCHER_VALID_INFO = "valid: ";

    public FixedAmountVoucher(double fixedValue) {
        super();
        this.value = fixedValue;
    }

    @Override
    public String getInfo() {
        StringBuilder voucherInfo = new StringBuilder();

        voucherInfo.append(FIXED_AMOUNT_VOUCHER_INFO_PREFIX + "\n")
                .append(FIXED_AMOUNT_VOUCHER_ID_INFO + code + "\n")
                .append(FIXED_AMOUNT_VOUCHER_VALUE_INFO + String.format("%.2f", value) + "\n")
                .append(FIXED_AMOUNT_VOUCHER_EXPIRE_INFO + expirationDate + "\n")
                .append(FIXED_AMOUNT_VOUCHER_VALID_INFO + isActive + "\n");

        return voucherInfo.toString();
    }
}
