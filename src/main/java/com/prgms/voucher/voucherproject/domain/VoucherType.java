package com.prgms.voucher.voucherproject.domain;

public enum VoucherType {
    FIXED(1),
    PERCENT(2);

    private int voucherType;

    VoucherType(int i) {
        this.voucherType = i;
    }

    public static VoucherType getSelectedVoucherType(int selectedNum){
        switch (selectedNum){
            case 1:
                return FIXED;
            case 2:
                return PERCENT;
            default:
                return null;
        }
    }
}
