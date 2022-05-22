package org.programmers.springbootbasic.core.command;

import org.programmers.springbootbasic.core.io.Console;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;

public class ExitCommand implements Command {

    @Override
    public boolean execute(Console console, DefaultVoucherService defaultVoucherService) {
        return false;
    }
}
