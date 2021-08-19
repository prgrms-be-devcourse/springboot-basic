package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.List;

public class StrategyList implements Command {
    @Override
    public boolean excute(Input input, Output output, VoucherService voucherService) {
        List<Voucher> vouchers = voucherService.getAllVoucher();
        output.voucherList(vouchers);
        return true;
    }
}
