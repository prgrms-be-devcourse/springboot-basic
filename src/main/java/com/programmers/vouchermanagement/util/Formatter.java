package com.programmers.vouchermanagement.util;

import com.programmers.vouchermanagement.customer.dto.CustomerResponse;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;

public class Formatter {
    //constants
    private static final String PERCENTAGE = " %";
    private static final String EMPTY = "";
    private static final String NO_CONTENT = "There is no %s stored yet!";

    private Formatter() {
    }

    private static final String CUSTOMER_PRESENTATION_FORMAT = """
            Customer ID : %s
            Customer Name : %s
            This Customer is %s Customer.
            -------------------------""";
    private static final String VOUCHER_PRESENTATION_FORMAT = """
            Voucher ID : %s
            Voucher Type : %s Discount Voucher
            Discount Amount : %s
            -------------------------""";

    public static String formatNoContent(String contentType) {
        return NO_CONTENT.formatted(contentType);
    }

    public static String formatCustomer(CustomerResponse customerResponse) {
        return CUSTOMER_PRESENTATION_FORMAT
                .formatted(customerResponse.customerId(), customerResponse.name(), customerResponse.customerType().name());
    }

    public static String formatVoucher(VoucherResponse voucherResponse) {
        return VOUCHER_PRESENTATION_FORMAT
                .formatted(voucherResponse.voucherId(),
                        voucherResponse.voucherType(),
                        voucherResponse.discountValue() +
                        markPercentage(voucherResponse.voucherType()));
    }

    private static String markPercentage(VoucherType voucherType) {
        if (voucherType.isPercent()) {
            return PERCENTAGE;
        }
        return EMPTY;
    }
}
