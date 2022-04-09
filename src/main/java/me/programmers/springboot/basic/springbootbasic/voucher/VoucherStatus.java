package me.programmers.springboot.basic.springbootbasic.voucher;

public enum VoucherStatus {
    FIXED,
    PERCENT,
    NON_VOUCHER;

    public static VoucherStatus getVoucherStatus(String s) {
        switch (s) {
            case "fixed":
                return FIXED;
            case "percent":
                return PERCENT;
            default:
                return NON_VOUCHER;
        }
    }
}
