package com.programmers.application.controller.voucher.command;

import com.programmers.application.controller.voucher.command.execution.VoucherExecution;

import java.io.IOException;


public enum Command {
    EXIT,
    CREATE,
    LIST,
    ;

    private VoucherExecution voucherExecution;

    public void executeVoucher() throws IOException {
        voucherExecution.execute();
    }

    protected void setVoucherExecution(VoucherExecution voucherExecution) {
        this.voucherExecution = voucherExecution;
    }
}
