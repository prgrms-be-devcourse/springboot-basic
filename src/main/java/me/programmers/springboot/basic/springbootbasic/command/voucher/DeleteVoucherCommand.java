package me.programmers.springboot.basic.springbootbasic.command.voucher;

import me.programmers.springboot.basic.springbootbasic.command.CommandStrategy;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;

public class DeleteVoucherCommand implements CommandStrategy {

    private final JdbcVoucherService voucherService;

    public DeleteVoucherCommand(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void operateCommand() {
        voucherService.delete();
    }
}
