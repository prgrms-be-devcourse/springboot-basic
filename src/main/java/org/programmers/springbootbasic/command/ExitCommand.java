package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.DefaultVoucherService;

public class ExitCommand implements Command {

    @Override
    public boolean execute(Console console, DefaultVoucherService defaultVoucherService) {
        return false;
    }
}
