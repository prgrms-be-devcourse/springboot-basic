package org.programmers.springbootbasic.core.command;

import org.programmers.springbootbasic.core.io.Console;
import org.programmers.springbootbasic.application.voucher.service.DefaultVoucherService;

public class ListCommand implements Command{
    @Override
    public boolean execute(Console console, DefaultVoucherService defaultVoucherService) {
        defaultVoucherService.getVoucherList().forEach(System.out::println);
        return true;
    }
}
