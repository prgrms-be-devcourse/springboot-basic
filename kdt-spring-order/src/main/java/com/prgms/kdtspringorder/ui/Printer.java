package com.prgms.kdtspringorder.ui;

import java.text.MessageFormat;
import java.util.List;

import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.ui.message.ErrorMessage;
import com.prgms.kdtspringorder.ui.message.OutputMessage;

public class Printer {
    public void printCommandList() {
        System.out.println((OutputMessage.VOUCHER_PROGRAM_COMMAND.getMessage()));
    }

    public void listVouchers(List<Voucher> vouchers) {
        vouchers
            .forEach(v -> System.out.println(
                OutputMessage.VOUCHER_ID.getMessage() + v.getVoucherId() + OutputMessage.VOUCHER_TYPE.getMessage()
                    + v.getClass().getSimpleName() + OutputMessage.DISCOUNT_AMOUNT.getMessage() + v.getDiscount()));
    }

    public void printInvalidCommandMessage() {
        System.out.println(ErrorMessage.INVALID_COMMAND.getMessage());
    }

    public void printInvalidVoucherType(String error) {
        System.out.println(MessageFormat.format("{0}\n{1}", ErrorMessage.INVALID_VOUCHER_TYPE.getMessage(), error));
    }

    public void printInvalidDiscount(String error) {
        System.out.println(error);
    }
}
