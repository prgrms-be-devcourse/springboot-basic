package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.UUID;

public class StrategyCreate implements Command {

    private static final String INPUT_PROMPT = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        // input voucher Type
        output.printRequestVoucherType();
        String inputVoucherType = input.inputString(INPUT_PROMPT);
        VoucherType voucherType = VoucherType.findVoucher(inputVoucherType);

        // input Voucher Value
        output.printRequestVoucherValue(voucherType);
        long value = Long.parseLong(input.inputString(INPUT_PROMPT));

        // create Voucher Instance
        voucherService.createVoucher(
                voucherType,
                UUID.randomUUID(),
                value);
        return true;
    }
}
