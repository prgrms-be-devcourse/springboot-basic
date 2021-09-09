package org.prgrms.kdtspringhomework.command;

import org.prgrms.kdtspringhomework.command.create.CreateVoucherStatus;
import org.prgrms.kdtspringhomework.io.Input;
import org.prgrms.kdtspringhomework.io.Output;
import org.prgrms.kdtspringhomework.voucher.service.VoucherService;

public class CreateCommand implements CommandStrategy {

    @Override
    public boolean execute(Input input, Output output, VoucherService voucherService) {
        boolean flag = true;
        try {
            output.inputVoucherTypeMessage();
            String voucherType = input.receiveUserInput();
            output.inputAmountMessage();
            long amount = 0L;
            try {
                amount = Long.parseLong(input.receiveUserInput());
            } catch (NumberFormatException e) {
                output.invalidAmount();
            }
            flag = CreateVoucherStatus.create(voucherService, voucherType, amount);
        } catch (IllegalArgumentException e) {
            output.invalidVoucherType();
        }
        return flag;
    }
}
