package com.programmers.vouchermanagement.util;

import com.programmers.vouchermanagement.customer.domain.Customer;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

public class Formatter {
    //constants
    private static final String PERCENTAGE = " %";
    private static final String EMPTY = "";
    private static final String NO_CONTENT = "There is no %s stored yet!";

    public static String formatNoContent(String contentType) {
        return NO_CONTENT.formatted(contentType);
    }

    public static String formatCustomer(Customer customer) {
        return """
                Customer ID : %s
                Customer Name : %s
                -------------------------"""
                .formatted(customer.getCustomerId(), customer.getName());
    }

    public static String formatVoucher(VoucherResponse voucherResponse) {
        return """
                Voucher ID : %s
                Voucher Type : %s Discount Voucher
                Discount Amount : %s
                -------------------------"""
                .formatted(voucherResponse.getVoucherId(),
                        voucherResponse.getVoucherTypeName(),
                        voucherResponse.getDiscountValue() +
                        (voucherResponse.isPercentVoucher() ? PERCENTAGE : EMPTY));
    }
}
