package com.prgms.kdtspringorder.ui;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

import com.prgms.kdtspringorder.domain.model.customer.Customer;
import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
import com.prgms.kdtspringorder.ui.message.ErrorMessage;
import com.prgms.kdtspringorder.ui.message.OutputMessage;

public class Printer {
    public void printCommandList() {
        System.out.println((OutputMessage.VOUCHER_PROGRAM_COMMAND.getMessage()));
    }

    public void printVoucherList(Map<UUID, Voucher> vouchers) {
        vouchers.forEach((id, voucher) -> {
            System.out.println(MessageFormat.format("{0}{1}{2}{3}{4}{5}", OutputMessage.VOUCHER_ID.getMessage(), id,
                OutputMessage.VOUCHER_TYPE.getMessage(), voucher.getClass().getSimpleName(),
                OutputMessage.DISCOUNT_AMOUNT.getMessage(), voucher.getDiscount()));
        });
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

    public void printBlacklist(Map<UUID, Customer> blackList) {
        System.out.println(OutputMessage.BLACKLIST.getMessage());
        blackList.forEach((id, customer) -> {
            System.out.println(MessageFormat.format("{0}{1}{2}{3}{4}{5}", OutputMessage.CUSTOMER_ID.getMessage(), id,
                OutputMessage.CUSTOMER_NAME.getMessage(), customer.getName(), OutputMessage.CUSTOMER_AGE.getMessage(),
                customer.getAge()));
        });
        System.out.println();
    }
}
