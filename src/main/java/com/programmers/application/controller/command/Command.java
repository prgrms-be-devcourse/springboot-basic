package com.programmers.application.controller.command;

import com.programmers.application.controller.command.execution.CreateVoucherExecution;
import com.programmers.application.controller.command.execution.ListVoucherExecution;
import com.programmers.application.controller.command.execution.VoucherExecution;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

import java.io.IOException;

public enum Command {
    EXIT, CREATE, LIST;

    public void executeVoucher(VoucherService voucherService, Command command, IO io) throws IOException {
        switch (command) {
            case EXIT -> io.write("Voucher 서비스를 종료합니다.");
            case CREATE -> {
                VoucherExecution voucherExecution = new CreateVoucherExecution(voucherService, io);
                voucherExecution.run();
            }
            case LIST -> {
                VoucherExecution voucherExecution = new ListVoucherExecution(voucherService, io);
                voucherExecution.run();
            }
        }
    }
}
