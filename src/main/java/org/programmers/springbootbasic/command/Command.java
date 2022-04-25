package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

@FunctionalInterface
public interface Command {
    boolean execute(Console console, VoucherService voucherService);
}
