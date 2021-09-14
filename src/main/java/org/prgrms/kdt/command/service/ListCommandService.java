package org.prgrms.kdt.command.service;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListCommandService implements Command {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public ListCommandService(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @Override
    public boolean execute() {
        List<Voucher> vouchers = voucherService.getAllVoucher();
        output.printVoucherList(vouchers);
        return true;
    }
}
