package com.programmers.application.domain.command;

import com.programmers.application.domain.command.execution.CreateVoucherExecution;
import com.programmers.application.domain.command.execution.ListVoucherExecution;
import com.programmers.application.domain.command.execution.VoucherExecution;
import com.programmers.application.io.IO;
import com.programmers.application.service.VoucherService;

public enum Command {
    EXIT, CREATE, LIST;

    public void executeVoucher(VoucherService voucherService, Command command, IO io) {
        switch (command) {
            case EXIT -> System.exit(1);
            case CREATE -> {
                VoucherExecution voucherExecution = new CreateVoucherExecution();
                voucherExecution.run(voucherService, io);
            }
            case LIST -> {
                VoucherExecution voucherExecution = new ListVoucherExecution();
                voucherExecution.run(voucherService, io);
            }
        }
    }
}
