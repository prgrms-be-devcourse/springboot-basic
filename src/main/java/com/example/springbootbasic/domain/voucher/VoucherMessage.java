package com.example.springbootbasic.domain.voucher;

import java.text.MessageFormat;

import static com.example.springbootbasic.util.CharacterUnit.ENTER;

public enum VoucherMessage {
    MENU(MessageFormat.format(
            "=== Voucher Program ==={0}" +
                    "Type **exit** to exit the program.{1}" +
                    "Type **create** to create a new voucher.{2}" +
                    "Type **list** to list all vouchers.{3}{4}",
            ENTER.getUnit(), ENTER.getUnit(), ENTER.getUnit(), ENTER.getUnit(), ENTER.getUnit())),
    CREATE(MessageFormat.format(
            "Type **fixed '{'number'}'** to create FixedAmountVoucher => ex) fixed 1000{0}" +
                    "Type **percent '{'number'}'** to create PercentDiscountVoucher => ex) percent 10{1}",
            ENTER.getUnit(), ENTER.getUnit()));

    private final String message;

    VoucherMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
