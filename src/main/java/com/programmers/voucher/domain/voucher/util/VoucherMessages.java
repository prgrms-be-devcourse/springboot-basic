package com.programmers.voucher.domain.voucher.util;

public interface VoucherMessages {
    String CREATED_NEW_VOUCHER = "Created new voucher";

    static String addVoucherId(String message, String voucherId) {
        return message + " VoucherID: " + voucherId;
    }

    static String addVoucher(String message, String voucher) {
        return message + " Voucher: " + voucher;
    }
}
