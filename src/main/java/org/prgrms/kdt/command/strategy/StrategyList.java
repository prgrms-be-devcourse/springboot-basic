package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Console;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StrategyList implements Command {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public StrategyList(Console console, VoucherService voucherService) {
        this.input = console;
        this.output = console;
        this.voucherService = voucherService;
    }
    @Override
    public boolean excute() {
        List<Voucher> vouchers = voucherService.getAllVoucher();
        output.voucherList(vouchers);
        return true;
    }
}
