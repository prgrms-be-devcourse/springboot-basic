package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.UUID;

public class StrategyCreate implements Command {

    private static final String INPUT_PROMPT = "> ";

    @Override
    public boolean excute(Input input, Output output, VoucherService voucherService) {
        output.inputVoucherType();
        String inputVoucherType = input.inputString(INPUT_PROMPT);
        VoucherType voucherType = VoucherType.findVoucher(inputVoucherType);

        output.inputVoucherValue(voucherType);
        long value = Long.parseLong(input.inputString(INPUT_PROMPT)); // TODO: 예외 처리,,, how to 잘?

        voucherService.createVoucher(
                voucherType,
                UUID.randomUUID(),
                value);
        return true;
    }
}
