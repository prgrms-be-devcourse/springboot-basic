package com.programmers.voucher.domain.voucher.util;

public final class VoucherMessages {
    public static final String CREATED_NEW_VOUCHER = "Created new voucher";
    public static final String CANNOT_FIND_VOUCHER = "Created new voucher";
    public static final String DELETE_VOUCHER = "Deleted Voucher: {}";

    private VoucherMessages() {
    }

    public static String addVoucherId(String message, String voucherId) {
        return message + " VoucherID: " + voucherId;
    }

    public static String addVoucher(String message, String voucher) {
        return message + " Voucher: " + voucher;
    }
}
