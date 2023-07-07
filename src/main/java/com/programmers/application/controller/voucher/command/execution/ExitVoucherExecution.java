package com.programmers.application.controller.voucher.command.execution;

import com.programmers.application.io.IO;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ExitVoucherExecution implements VoucherExecution{

    private final IO io;

    public ExitVoucherExecution(IO io) {
        this.io = io;
    }

    @Override
    public void execute() throws IOException {
        io.write("Voucher 서비스를 종료합니다.");
    }
}
