package org.programmers.springbootbasic.core.command;

import org.programmers.springbootbasic.core.io.Console;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;

@FunctionalInterface
public interface Command {
    boolean execute(Console console, DefaultVoucherService defaultVoucherService);
}
