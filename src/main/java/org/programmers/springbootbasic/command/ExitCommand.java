package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

public class ExitCommand implements Command {

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        return false;
    }
}
