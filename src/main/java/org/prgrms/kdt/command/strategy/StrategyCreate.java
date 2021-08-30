package org.prgrms.kdt.command.strategy;

import org.prgrms.kdt.command.domain.Command;
import org.prgrms.kdt.command.io.Input;
import org.prgrms.kdt.command.io.Output;
import org.prgrms.kdt.voucher.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;

import java.util.Optional;
import java.util.UUID;

public class StrategyCreate implements Command {

    private static final String INPUT_PROMPT = "> ";

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        Optional<VoucherType> voucherType;

        while(true) {
            output.printRequestVoucherType();

            String inputVoucherType = input.inputString(INPUT_PROMPT);
            voucherType = VoucherType.findVoucher(inputVoucherType);

            if (voucherType.isEmpty()) { // TODO: Refactoring
                output.printInputError();
            } else {
                break;
            }
        }


        output.printRequestVoucherValue(voucherType.get());
        long value = Long.parseLong(input.inputString(INPUT_PROMPT)); // TODO: 예외 처리,,, how to 잘?

        voucherService.createVoucher(
                voucherType.get(),
                UUID.randomUUID(),
                value);
        return true;
    }
}
