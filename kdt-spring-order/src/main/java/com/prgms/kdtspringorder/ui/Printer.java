package com.prgms.kdtspringorder.ui;

import java.util.List;

import com.prgms.kdtspringorder.domain.model.voucher.Voucher;
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
}
