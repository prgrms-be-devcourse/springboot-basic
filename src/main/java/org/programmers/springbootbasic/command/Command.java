package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.DefaultVoucherService;

@FunctionalInterface
public interface Command {
    boolean execute(Console console, DefaultVoucherService defaultVoucherService);
}
