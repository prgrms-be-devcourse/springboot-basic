package org.programmers.springbootbasic;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

public class ExitCommand implements Command {

    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        return false;
    }
}
