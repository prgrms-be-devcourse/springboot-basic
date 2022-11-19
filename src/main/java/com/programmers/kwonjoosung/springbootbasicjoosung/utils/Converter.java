package com.programmers.kwonjoosung.springbootbasicjoosung.utils;

import com.programmers.kwonjoosung.springbootbasicjoosung.model.customer.Customer;
import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.*;

import java.util.UUID;

public class Converter {

    private static final String NEXT = System.lineSeparator();
    private static final String SPACE = " ";
    private static final String COMMA = ",";

    private Converter() {

    }

    public static Customer toCustomer(String text) {
        String[] textCustomer = text.split(COMMA);
        return new Customer(UUID.fromString(textCustomer[0]), textCustomer[1]);
    }

    public static Voucher toVoucher(String text) {
        String[] textVoucher = text.split(SPACE);
        return VoucherFactory.createVoucher(
                VoucherType.getVoucherType(textVoucher[0]),
                UUID.fromString(textVoucher[1]),
                Long.parseLong(textVoucher[2]));
    }

    public static String convertText(Voucher voucher) {
        return voucher.getVoucherType().name() + SPACE +
                voucher.getVoucherId() + SPACE +
                voucher.getDiscount() + NEXT;
    }

}
