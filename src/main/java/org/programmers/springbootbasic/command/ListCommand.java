package org.programmers.springbootbasic.command;

import org.programmers.springbootbasic.io.Console;
import org.programmers.springbootbasic.voucher.service.VoucherService;

public class ListCommand implements Command{
    @Override
    public boolean execute(Console console, VoucherService voucherService) {
        voucherService.getVoucherList().forEach(System.out::println);
        return true;
    }
}
